import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-tester',
  templateUrl: './tester.component.html',
  styleUrls: ['./tester.component.css']
})
export class TesterComponent implements OnInit {
  constructor(private fb: FormBuilder) { }

  formGroup = this.fb.group({
    file: this.fb.control(null)
  })

  ngOnInit(): void {
  }

}
