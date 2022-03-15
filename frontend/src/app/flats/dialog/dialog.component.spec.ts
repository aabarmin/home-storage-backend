import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FlatDialogComponent } from './dialog.component';

describe('DialogComponent', () => {
  let component: FlatDialogComponent;
  let fixture: ComponentFixture<FlatDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FlatDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FlatDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
