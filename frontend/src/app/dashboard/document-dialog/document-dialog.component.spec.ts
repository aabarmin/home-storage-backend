import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardDocumentDialogComponent } from './document-dialog.component';

describe('DocumentDialogComponent', () => {
  let component: DashboardDocumentDialogComponent;
  let fixture: ComponentFixture<DashboardDocumentDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DashboardDocumentDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardDocumentDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
