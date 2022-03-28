import {
  AfterViewInit,
  Component,
  ElementRef,
  forwardRef,
  HostBinding,
  Input,
  OnDestroy,
  ViewChild,
} from '@angular/core';
import {
  ControlValueAccessor,
  NgControl,
  NG_VALUE_ACCESSOR,
} from '@angular/forms';
import { MatFormFieldControl } from '@angular/material/form-field';
import {
  BehaviorSubject,
  filter,
  fromEvent,
  map,
  mergeMap,
  Observable,
  Subject,
} from 'rxjs';
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
  providers: [
    {
      provide: MatFormFieldControl,
      useExisting: FileUploadComponent,
    },
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: forwardRef(() => FileUploadComponent),
    },
  ],
})
export class FileUploadComponent
  implements
    OnDestroy,
    AfterViewInit,
    MatFormFieldControl<string>,
    ControlValueAccessor
{
  file$: BehaviorSubject<string | null> = new BehaviorSubject<string | null>(
    null
  );
  fileInfo$: Observable<FileId>;

  stateChanges = new Subject<void>();
  static nextId = 0;

  /**
   * Element which is used to upload files.
   */
  @ViewChild('fileControl')
  fileControl?: ElementRef;

  /**
   * The function from ControlValueAccessor which is callend when the value is changed.
   */
  private _onChange?: any;

  @HostBinding()
  id: string = `home-is-file-upload-input-${FileUploadComponent.nextId++}`;

  ngControl: NgControl | null = null;

  controlType = 'home-is-file-upload';
  autofilled?: boolean | undefined;

  @Input('aria-describedby')
  userAriaDescribedBy?: string | undefined;

  private _value: string | null = null;
  private _placeholder: string = '';
  private _focused: boolean = false;
  private _empty: boolean = true;
  private _required: boolean = false;
  private _disabled: boolean = false;
  private _errorState: boolean = false;

  constructor(private fileService: FileService) {
    this.fileInfo$ = this.file$.pipe(
      filter((file) => file != null),
      map((file) => file as string),
      mergeMap((fileId) => this.fileService.findFileId(fileId))
    );
  }

  writeValue(obj: any): void {
    const value = obj as string | null;
    this.value = value;
  }

  registerOnChange(fn: any): void {
    this._onChange = fn;
  }

  registerOnTouched(fn: any): void {}

  onFileSelect(): void {
    this.fileControl?.nativeElement.click();
  }

  onFileRemove(): void {
    this.value = null;
  }

  onFileRemoveMenuOpen(): void {
    throw new Error('Not implemented yet');
  }

  ngAfterViewInit(): void {
    fromEvent(this.fileControl?.nativeElement, 'change').subscribe(
      (nativeEvent) => {
        const event = nativeEvent as FileSelectionEvent;
        const files = event.currentTarget.files;
        if (files.length == 0) {
          return;
        }
        const file = files.item(0) as File;
        this.fileService.upload(file).subscribe((fileId: FileId) => {
          this.value = fileId.fileId;
        });
      }
    );
  }

  ngOnDestroy(): void {
    this.stateChanges.complete();
  }

  @Input()
  get value(): string | null {
    return this._value;
  }

  set value(value: string | null) {
    this._value = value;
    this.file$.next(value);
    this.stateChanges.next();
    if (this._onChange) {
      this._onChange(value);
    }
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
    }
  }
}
