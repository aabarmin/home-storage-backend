import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FlatsListComponent } from './list.component';

describe('ListComponent', () => {
  let component: FlatsListComponent;
  let fixture: ComponentFixture<FlatsListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FlatsListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FlatsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
