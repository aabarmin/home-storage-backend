package dev.abarmin.home.is.backend.readings.controller.documents.importer;

import dev.abarmin.home.is.backend.binary.storage.domain.FileInfo;
import dev.abarmin.home.is.backend.binary.storage.service.BinaryService;
import dev.abarmin.home.is.backend.readings.controller.documents.file.ImportFile;
import dev.abarmin.home.is.backend.readings.controller.documents.file.ImportFileFinder;
import dev.abarmin.home.is.backend.readings.domain.Device;
import dev.abarmin.home.is.backend.readings.domain.DeviceReading;
import dev.abarmin.home.is.backend.readings.domain.Flat;
import dev.abarmin.home.is.backend.readings.model.ImportConfiguration;
import dev.abarmin.home.is.backend.readings.model.ImportSource;
import dev.abarmin.home.is.backend.readings.model.device.ImportDevice;
import dev.abarmin.home.is.backend.readings.model.device.ImportDeviceFeature;
import dev.abarmin.home.is.backend.readings.model.document.ImportDocument;
import dev.abarmin.home.is.backend.readings.model.flat.ImportFlat;
import dev.abarmin.home.is.backend.readings.service.DeviceReadingService;
import dev.abarmin.home.is.backend.readings.service.DeviceService;
import dev.abarmin.home.is.backend.readings.service.FlatService;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Aleksandr Barmin
 */
@Component
@RequiredArgsConstructor
public class DocumentImporter {
  private final FlatService flatService;
  private final DeviceService deviceService;
  private final ImportFileFinder fileFinder;
  private final BinaryService binaryService;
  private final DeviceReadingService readingService;

  public void importDocuments(final ImportConfiguration configuration) {
    for (ImportSource source : configuration.getSources()) {
      final Flat flat = importFlat(source.getFlat());
      importDevices(flat, source.getDevices());
      for (ImportDocument document : source.getDocuments()) {
        importDocuments(flat, document);
      }
    }
  }

  private void importDocuments(Flat flat, ImportDocument document) {
    final Collection<ImportFile> files = fileFinder.findFiles(
        Path.of(document.getPath()),
        document.getNamePattern()
    );

    for (ImportFile file : files) {
      // find reading first or create
      final DeviceReading deviceReading = findReading(
          flat,
          deviceService.findByAlias(document.getDeviceAlias()).get(),
          file.date()
      );
      // upload the file
      final FileInfo uploadedFile = binaryService.upload(file.filePath());
      // attach to the proper field
      if (document.getType() == ImportDeviceFeature.INVOICES) {
        readingService.save(deviceReading.withInvoiceFile(
            uploadedFile
        ));
      } else if (document.getType() == ImportDeviceFeature.RECEIPTS) {
        readingService.save(deviceReading.withReceiptFile(
            uploadedFile
        ));
      }
    }
  }

  private DeviceReading findReading(Flat flat, Device device, LocalDate date) {
    return readingService.findDeviceReadingByFlatAndDeviceAndDate(flat, device, date)
        .orElseGet(() -> readingService.save(new DeviceReading(
            device,
            flat,
            date
        )));
  }

  private void importDevices(Flat flat, Collection<ImportDevice> devices) {
    for (ImportDevice device : devices) {
      deviceService.findByAlias(device.getAlias())
          .orElseGet(() -> deviceService.save(new Device(
              null,
              device.getName(),
              device.getAlias(),
              flat.getId(),
              hasFeature(device, ImportDeviceFeature.READINGS),
              hasFeature(device, ImportDeviceFeature.INVOICES),
              hasFeature(device, ImportDeviceFeature.RECEIPTS)
          )));
    }
  }

  private boolean hasFeature(ImportDevice device, ImportDeviceFeature feature) {
    return device.getFeatures().contains(feature);
  }

  private Flat importFlat(ImportFlat flat) {
    return flatService.findOneByAlias(flat.getAlias())
        .orElseGet(() -> flatService.save(new Flat(
            null,
            flat.getName(),
            flat.getAlias()
        )));
  }
}
