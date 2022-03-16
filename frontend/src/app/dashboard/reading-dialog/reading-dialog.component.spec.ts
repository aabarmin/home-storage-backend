import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardReadingDialogComponent } from './reading-dialog.component';

describe('ReadingDialogComponent', () => {
  let component: DashboardReadingDialogComponent;
  let fixture: ComponentFixture<DashboardReadingDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DashboardReadingDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardReadingDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
