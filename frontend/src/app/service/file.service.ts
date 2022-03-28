import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { FileId } from '../model/file-id';
import { LocalStorageService } from './local-storage.service';

@Injectable({
  providedIn: 'root',
})
export class FileService extends LocalStorageService<FileId> {
  /**
   * Upload the file to the server.
   * @TODO: shouldn't it be rewritten to use Observable<File>?
   *
   * @param file
   * @returns
   */
  public upload(file: File): Observable<FileId> {
    const items = this.getStorage('files');
    const fileInfo: FileId = {
      fileId: String(items.length),
      fileName: file.name,
      fileType: file.type,
    };
    items.push(fileInfo);
    this.setStorage('files', items);

    return of(fileInfo);
  }

  /**
   * Get information about a file by its id.
   *
   * @param file$
   */
  public findFileId(fileId: string): Observable<FileId> {
    const items = this.getStorage('files');
    const filtered = items.filter((file) => file.fileId == fileId);
    return of(filtered[0]);
  }
}
