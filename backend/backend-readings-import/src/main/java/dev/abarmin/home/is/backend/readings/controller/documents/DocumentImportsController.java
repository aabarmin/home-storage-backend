package dev.abarmin.home.is.backend.readings.controller.documents;

import dev.abarmin.home.is.backend.binary.storage.domain.FileInfo;
import dev.abarmin.home.is.backend.binary.storage.service.BinaryService;
import dev.abarmin.home.is.backend.binary.storage.service.BinaryServiceHelper;
import dev.abarmin.home.is.backend.binary.storage.service.FileInfoService;
import dev.abarmin.home.is.backend.readings.controller.documents.checker.PreImportChecker;
import dev.abarmin.home.is.backend.readings.controller.documents.importer.DocumentImporter;
import dev.abarmin.home.is.backend.readings.controller.documents.reader.ConfigFileReader;
import dev.abarmin.home.is.backend.readings.model.ImportConfiguration;
import java.nio.file.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Aleksandr Barmin
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/import/documents")
public class DocumentImportsController {
  private final ConfigFileReader configReader;
  private final PreImportChecker configChecker;
  private final DocumentImporter documentImporter;
  private final BinaryService binaryService;
  private final BinaryServiceHelper binaryServiceHelper;
  private final FileInfoService fileInfoService;

  @GetMapping
  public ModelAndView uploadConfig(final ModelAndView modelAndView) {
    modelAndView.setViewName("documents/upload");
    return modelAndView;
  }

  @PostMapping
  public ModelAndView uploadAndCheck(final ModelAndView modelAndView,
                                     final @RequestParam("config") MultipartFile configFile) {

    final Path temporaryFile = binaryServiceHelper.uploadToTemporaryFolder(configFile);
    final FileInfo uploadedFile = binaryService.upload(temporaryFile);
    final ImportConfiguration configuration = configReader.read(uploadedFile);
    modelAndView.addObject(
        "validation",
        configChecker.validateConfiguration(configuration)
    );
    modelAndView.addObject(
        "flatValidation",
        configChecker.validateFlats(configuration)
    );
    modelAndView.addObject(
        "deviceValidation",
        configChecker.validateDevices(configuration)
    );
    modelAndView.addObject(
        "documentValidation",
        configChecker.validateFiles(configuration)
    );
    modelAndView.addObject(
        "configFile",
        uploadedFile.id()
    );
    modelAndView.setViewName("documents/prepare");
    return modelAndView;
  }

  @PostMapping("/perform")
  public ModelAndView performImport(final ModelAndView modelAndView,
                                    final @RequestParam("configFile") int configFileId) {

    final FileInfo configFileInfo = fileInfoService.findById(configFileId)
        .orElseThrow();
    final ImportConfiguration configuration = configReader.read(configFileInfo);
    documentImporter.importDocuments(configuration);

    modelAndView.setViewName("documents/perform");
    return modelAndView;
  }
}
