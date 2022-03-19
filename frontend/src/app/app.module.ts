import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppAngularModule } from './app.angular';
import { ToolbarComponent } from './toolbar/toolbar.component';
import { NavigatorComponent } from './navigator/navigator.component';
import { FlatsListComponent } from './flats/list/list.component';
import { FlatDialogComponent } from './flats/dialog/dialog.component';
import { FlatFormComponent } from './flats/form/form.component';
import { ReactiveFormsModule } from '@angular/forms';
import { StringifyPipe } from './pipes/stringify.pipe';
import { DeviceListComponent } from './devices/list/list.component';
import { DeviceDialogComponent } from './devices/dialog/dialog.component';
import { DeviceFormComponent } from './devices/form/form.component';
import { DashboardComponent } from './dashboard/dashboard/dashboard.component';
import { MonthDataComponent } from './dashboard/month-data/month-data.component';
import { DashboardReadingDialogComponent } from './dashboard/reading-dialog/reading-dialog.component';
import { DashboardDocumentDialogComponent } from './dashboard/document-dialog/document-dialog.component';
import { DashboardDeviceDialogComponent } from './dashboard/device-dialog/device-dialog.component';
import { FileUploadComponent } from './controls/file-upload/file-upload.component';
import { TesterComponent } from './controls/tester/tester.component';

@NgModule({
  declarations: [
    AppComponent,
    ToolbarComponent,
    NavigatorComponent,
    FlatsListComponent,
    FlatDialogComponent,
    FlatFormComponent,
    StringifyPipe,
    DeviceListComponent,
    DeviceDialogComponent,
    DeviceFormComponent,
    DashboardComponent,
    MonthDataComponent,
    DashboardReadingDialogComponent,
    DashboardDocumentDialogComponent,
    DashboardDeviceDialogComponent,
    FileUploadComponent,
    TesterComponent
  ],
  imports: [
    AppAngularModule, 

    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
