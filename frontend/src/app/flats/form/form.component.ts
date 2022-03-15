import { Output } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Flat } from 'src/app/model/flat';

@Component({
  selector: 'app-flats-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class FlatFormComponent implements OnInit {
  flatForm: FormGroup = new FormGroup({
    title: new FormControl('', [Validators.required]),
    alias: new FormControl('', [Validators.required, Validators.pattern('[a-z]+')])
  });

  @Output()
  onValidityChange: EventEmitter<boolean> = new EventEmitter<boolean>();

  constructor() { 
    
  }
  
  ngOnInit(): void {
    this.flatForm.valueChanges.subscribe(change => {
      this.onValidityChange.emit(this.flatForm.valid);
    })
  }

  public getFormValue(): Flat {
    return this.flatForm.value as Flat;
  }

}
