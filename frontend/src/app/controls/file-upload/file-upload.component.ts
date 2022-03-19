import { Input } from '@angular/core';
import { ViewChild } from '@angular/core';
import { ElementRef } from '@angular/core';
import { HostBinding } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { AfterViewInit } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { NgControl } from '@angular/forms';
import { MatFormFieldControl } from '@angular/material/form-field';
import { MatSelectionList, MatSelectionListChange } from '@angular/material/list';
import { MatMenu } from '@angular/material/menu';
import { map } from 'rxjs';
import { fromEvent } from 'rxjs';
import { BehaviorSubject } from 'rxjs';
import { forkJoin } from 'rxjs';
import { mergeAll } from 'rxjs';
import { flatMap } from 'rxjs';
import { filter } from 'rxjs';
import { Subject } from 'rxjs';
import { Observable } from 'rxjs';
import { FileId } from 'src/app/model/file-id';
import { FileService } from 'src/app/service/file.service';

interface FileSelectionTarget {
  files: FileList;
}

interface FileSelectionEvent {
  target: FileSelectionTarget;
  currentTarget: FileSelectionTarget;
  srcElement: FileSelectionTarget;
}

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.css'],
  providers: [{
    provide: MatFormFieldControl, 
    useExisting: FileUploadComponent
  }]
})
export class FileUploadComponent implements OnDestroy, AfterViewInit, MatFormFieldControl<FileId> {
  file$: BehaviorSubject<FileId | null> = new BehaviorSubject<FileId | null>(null);

  stateChanges = new Subject<void>();
  static nextId = 0;

  /**
   * Element which is used to upload files. 
   */
  @ViewChild("fileControl")
  fileControl?: ElementRef;
  
  @HostBinding()
  id: string = `home-is-file-upload-input-${FileUploadComponent.nextId++}`;
  
  ngControl: NgControl | null = null;
  
  controlType = "home-is-file-upload";
  autofilled?: boolean | undefined;
  
  @Input('aria-describedby')
  userAriaDescribedBy?: string | undefined;

  private _value: FileId | null = null;
  private _placeholder: string = "";
  private _focused: boolean = false;
  private _empty: boolean = true;
  private _required: boolean = false;
  private _disabled: boolean = false;
  private _errorState: boolean = false;

  constructor(
    private fileService: FileService
  ) {
    
  }

  onFileSelect(): void {
    this.fileControl?.nativeElement.click();
  }

  onFileRemove(): void {
    this.value = null;
  }

  onFileRemoveMenuOpen(): void {
    
  }

  ngAfterViewInit(): void {
    fromEvent(this.fileControl?.nativeElement, "change").subscribe((nativeEvent) => {
      const event = nativeEvent as FileSelectionEvent;
      const files = event.currentTarget.files;
      if (files.length == 0) {
        return;
      }
      const file = files.item(0) as File;
      this.fileService.upload(file).subscribe((fileId: FileId) => {
        this.value = fileId;
      })
    })
  }

  ngOnDestroy(): void {
    this.stateChanges.complete();
  }

  @Input()
  get value(): FileId | null {
    return this._value;
  }

  set value(value: FileId | null) {
    this._value = value;
    this.file$.next(value);
    this.stateChanges.next();
  }

  @Input()
  get placeholder(): string {
    return this._placeholder;
  }

  set placeholder(value: string) {
    this._placeholder = value;
    this.stateChanges.next();
  }

  get focused(): boolean {
    return this._focused;
  }

  set focused(focused: boolean) {
    this._focused = focused;
    this.stateChanges.next();
  }

  get empty(): boolean {
    return this._empty;
  }

  @HostBinding('class.floating')
  get shouldLabelFloat(): boolean {
    return this.focused || !this.empty;
  }

  @Input()
  get required(): boolean {
    return this._required;
  }

  set required(value: boolean) {
    this._required = value;
    this.stateChanges.next();
  }

  @Input()
  get disabled(): boolean {
    return this._disabled;
  }

  set disabled(disabled: boolean) {
    this._disabled = disabled;
    this.stateChanges.next();
  }

  get errorState(): boolean {
    return this._errorState;
  }
  
  setDescribedByIds(ids: string[]): void {
    // throw new Error('Method not implemented.');
  }
  onContainerClick(event: MouseEvent): void {
    if (this.file$.getValue() == null) {
      // no value selected, start the selection dialog
      this.onFileSelect();
    } else {
      
    }
  }

}
