import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardDeviceDialogComponent } from './device-dialog.component';

describe('DeviceDialogComponent', () => {
  let component: DashboardDeviceDialogComponent;
  let fixture: ComponentFixture<DashboardDeviceDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DashboardDeviceDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardDeviceDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
