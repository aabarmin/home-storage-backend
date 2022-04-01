import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FlatFormComponent } from './form.component';

describe('FormComponent', () => {
  let component: FlatFormComponent;
  let fixture: ComponentFixture<FlatFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FlatFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FlatFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
