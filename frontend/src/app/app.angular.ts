import { NgModule } from "@angular/core";
import { MatToolbarModule } from "@angular/material/toolbar";
import { MatSidenavModule } from "@angular/material/sidenav";
import { MatIconModule } from "@angular/material/icon";
import { MatButtonModule } from "@angular/material/button"
import { MatListModule } from "@angular/material/list";
import { MatTableModule } from "@angular/material/table";
import { MatDialogModule } from "@angular/material/dialog";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatCommonModule } from "@angular/material/core";
import { MatInputModule } from "@angular/material/input";
import { MatSelectModule } from "@angular/material/select";
import { MatSlideToggleModule } from "@angular/material/slide-toggle";
import { MatExpansionModule } from "@angular/material/expansion";
import { MatGridListModule } from "@angular/material/grid-list";
import { MatCardModule } from "@angular/material/card";
import { MatDatepickerModule } from "@angular/material/datepicker";
import { MatNativeDateModule } from "@angular/material/core";
import { NgxMatFileInputModule } from "@angular-material-components/file-input";

@NgModule({
    imports: [
        MatToolbarModule,
        MatSidenavModule,
        MatIconModule,
        MatButtonModule,
        MatListModule,
        MatTableModule,
        MatDialogModule,
        MatFormFieldModule,
        MatCommonModule,
        MatInputModule,
        MatSelectModule,
        MatSlideToggleModule,
        MatExpansionModule,
        MatGridListModule,
        MatCardModule,
        MatDatepickerModule,
        MatNativeDateModule,
        NgxMatFileInputModule
    ],
    exports: [
        MatToolbarModule,
        MatSidenavModule,
        MatIconModule,
        MatButtonModule,
        MatListModule,
        MatTableModule,
        MatDialogModule,
        MatFormFieldModule,
        MatCommonModule,
        MatInputModule,
        MatSelectModule,
        MatSlideToggleModule,
        MatExpansionModule,
        MatGridListModule,
        MatCardModule,
        MatDatepickerModule,
        MatNativeDateModule,
        NgxMatFileInputModule
    ]
})
export class AppAngularModule {}