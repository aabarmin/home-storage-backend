import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppAngularModule } from './app.angular';
import { AppComponent } from './app.component';
import { FileUploadComponent } from './controls/file-upload/file-upload.component';
import { TesterComponent } from './controls/tester/tester.component';
import { DashboardComponent } from './dashboard/dashboard/dashboard.component';
import { DashboardDeviceDialogComponent } from './dashboard/device-dialog/device-dialog.component';
import { MonthDataComponent } from './dashboard/month-data/month-data.component';
import { DeviceDialogComponent } from './devices/dialog/dialog.component';
import { DeviceFormComponent } from './devices/form/form.component';
import { DeviceListComponent } from './devices/list/list.component';
import { FlatDialogComponent } from './flats/dialog/dialog.component';
import { FlatFormComponent } from './flats/form/form.component';
import { FlatsListComponent } from './flats/list/list.component';
import { NavigatorComponent } from './navigator/navigator.component';
import { StringifyPipe } from './pipes/stringify.pipe';
import { ToolbarComponent } from './toolbar/toolbar.component';

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
    DashboardDeviceDialogComponent,
    FileUploadComponent,
    TesterComponent,
  ],
  imports: [
    AppAngularModule,

    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
