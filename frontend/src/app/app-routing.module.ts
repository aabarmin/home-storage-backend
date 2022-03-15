import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DeviceListComponent } from './devices/list/list.component';
import { FlatsListComponent } from './flats/list/list.component';

const routes: Routes = [
  {
    path: "flats",
    component: FlatsListComponent
  },
  {
    path: "devices",
    component: DeviceListComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
