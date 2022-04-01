import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { VersionInfo } from '../model/version-info';

@Injectable({
  providedIn: 'root',
})
export class VersionService {
  private readonly backendUrl;

  constructor(private http: HttpClient) {
    this.backendUrl = `${environment.backendBase}/health/env`;
  }

  public getEnvironmentInfo(): Observable<VersionInfo> {
    return this.http.get<VersionInfo>(this.backendUrl);
  }
}
