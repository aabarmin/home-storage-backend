import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { environment } from 'src/environments/environment';
import { FileId } from '../model/file-id';

@Injectable({
  providedIn: 'root',
})
export class FileService {
  private readonly backendUrl: string;

  constructor(private http: HttpClient) {
    this.backendUrl = `${environment.backendBase}/files`;
  }

  public getDownloadUrl(file: FileId): Observable<string> {
    return of(`${this.backendUrl}/${file.fileId}/download`);
  }

  /**
   * Upload the file to the server.
   * @TODO: shouldn't it be rewritten to use Observable<File>?
   *
   * @param file
   * @returns
   */
  public upload(file: File): Observable<FileId> {
    const form = new FormData();
    form.append('file', file);

    return this.http.post<FileId>(this.backendUrl, form);
  }

  /**
   * Get information about a file by its id.
   *
   * @param file
   */
  public findFileId(fileId: number): Observable<FileId> {
    const url = `${this.backendUrl}/${fileId}`;
    return this.http.get<FileId>(url);
  }
}
