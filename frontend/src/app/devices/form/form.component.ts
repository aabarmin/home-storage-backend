import { Output } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { Device } from 'src/app/model/device';
import { Flat } from 'src/app/model/flat';
import { FlatService } from 'src/app/service/flat.service';

@Component({
  selector: 'app-devices-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class DeviceFormComponent implements OnInit {
  @Output()
  onValidityChange: EventEmitter<boolean> = new EventEmitter();

  formGroup: FormGroup = new FormGroup({ 
    title: new FormControl('', [Validators.required]),
    flat: new FormControl('', [Validators.required]),
    alias: new FormControl('', [Validators.required, Validators.pattern('[a-z]+')]),
    needInvoices: new FormControl(true),
    needBills: new FormControl(true),
    needReadings: new FormControl(true)
  });

  flats$: Observable<Flat[]>

  constructor(
    private flatService: FlatService
  ) { 
    this.flats$ = this.flatService.findAll();
  }

  ngOnInit(): void {
    this.formGroup.valueChanges.subscribe(() => {
      console.log(this.formGroup.value);
      this.onValidityChange.next(this.formGroup.valid);
    })
  }

  public getFormValue(): Device {
    return this.formGroup.value as Device;
  }
}
