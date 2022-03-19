import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TesterComponent } from './controls/tester/tester.component';
import { DashboardComponent } from './dashboard/dashboard/dashboard.component';
import { DeviceListComponent } from './devices/list/list.component';
import { FlatsListComponent } from './flats/list/list.component';

const routes: Routes = [
  {
    path: "",
    component: DashboardComponent
  },
  {
    path: "flats",
    component: FlatsListComponent
  },
  {
    path: "devices",
    component: DeviceListComponent
  },
  {
    path: "tester",
    component: TesterComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
