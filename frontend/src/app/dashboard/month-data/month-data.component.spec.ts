import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MonthDataComponent } from './month-data.component';

describe('MonthDataComponent', () => {
  let component: MonthDataComponent;
  let fixture: ComponentFixture<MonthDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MonthDataComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MonthDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
