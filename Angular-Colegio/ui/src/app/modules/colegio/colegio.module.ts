import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

//PRIMENG
import {DataScrollerModule} from 'primeng/primeng';
import {DropdownModule} from 'primeng/primeng';

// Router
import { ProvidersRouter } from './colegio.router';

// Components
import { InitComponent } from './Init/init.component';
import { StudentComponent } from './Student/List/student.component';
import { StudentCreateComponent } from './Student/Create/student.create.component';
import { StudentUpdateComponent } from './Student/Update/student.update.component';
import { InputCommonsComponent } from './Commons/Input/input.component';
import { SelectOneMenuCommonsComponent } from './Commons/SelectOneMenu/selectOneMenu.component';
import { TableCommonsComponent } from './Commons/Table/table.component';
import { LoadingCommonsComponent } from './Commons/Loading/loading.component';

//Services
import { StudentService } from './services/student.service';

@NgModule({
    imports:
    [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        ProvidersRouter,
        DataScrollerModule,
        DropdownModule
    ],
    declarations:
    [
        InitComponent,
        StudentComponent,
        StudentCreateComponent,
        StudentUpdateComponent,
        InputCommonsComponent,
        SelectOneMenuCommonsComponent,
        TableCommonsComponent,
        LoadingCommonsComponent
    ],
    providers:
    [
        StudentService
    ]
})
export class ColegioModule { }
