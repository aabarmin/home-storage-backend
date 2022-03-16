import { Injectable } from '@angular/core';
import { of } from 'rxjs';
import { Observable } from 'rxjs';
import { FileId } from '../model/file-id';
import { LocalStorageService } from './local-storage.service';

@Injectable({
  providedIn: 'root'
})
export class FileService extends LocalStorageService<FileId> {

  public upload(file: File): Observable<FileId> {
    const items = this.getStorage('files');
    const fileId: FileId = {
      fileId: String(items.length)
    }
    items.push(fileId);
    this.setStorage('files', items);

    return of(fileId);
  }
}
