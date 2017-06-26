import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

//PRIMENG
import {DataScrollerModule} from 'primeng/primeng';
import {DropdownModule} from 'primeng/primeng';
import {CalendarModule} from 'primeng/primeng';
import {TooltipModule} from 'primeng/primeng';
import {AccordionModule} from 'primeng/primeng';
import {DialogModule} from 'primeng/primeng';
import {InputMaskModule} from 'primeng/primeng';
import {ListboxModule} from 'primeng/primeng';

// Router
import { ProvidersRouter } from './colegio.router';

// Components
import { InitComponent } from './Init/init.component';
import { InputCommonsComponent } from './Commons/Input/input.component';
import { SelectOneMenuCommonsComponent } from './Commons/SelectOneMenu/selectOneMenu.component';
import { TableCommonsComponent } from './Commons/Table/table.component';
import { LoadingCommonsComponent } from './Commons/Loading/loading.component';

//Students component
import { StudentComponent } from './Student/List/student.component';
import { StudentCreateComponent } from './Student/Create/student.create.component';

//Courses component
import { CourseComponent } from './Course/List/course.component';
import { CourseCreateComponent } from './Course/Create/course.create.component';

//PaymentPlan component
import { PaymentPlanComponent } from './PaymentPlan/List/paymentPlan.component';
import { PaymentPlanCreateComponent } from './PaymentPlan/Create/paymentPlan.create.component';

//PaymentRecord component
import { PaymentRecordComponent } from './PaymentRecord/paymentRecord.component';

//Services
import { StudentService } from './services/student.service';
import { CourseService } from './services/course.service';
import { YearService } from './services/year.service';
import { LevelService } from './services/level.service';
import { DivisionService } from './services/division.service';
import { DocumentTypeService } from './services/documentType.service';
import { PaymentPlanService } from './services/paymentPlan.service';

@NgModule({
    imports:
    [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        ProvidersRouter,
        DataScrollerModule,
        DropdownModule,
        CalendarModule,
        TooltipModule,
        AccordionModule,
        DialogModule,
        InputMaskModule,
        ListboxModule
    ],
    declarations:
    [
        InitComponent,
        StudentComponent,
        StudentCreateComponent,
        InputCommonsComponent,
        SelectOneMenuCommonsComponent,
        TableCommonsComponent,
        LoadingCommonsComponent,
        CourseComponent,
        CourseCreateComponent,
        PaymentPlanComponent,
        PaymentPlanCreateComponent,
        PaymentRecordComponent
    ],
    providers:
    [
        StudentService,
        CourseService,
        YearService,
        LevelService,
        DivisionService,
        DocumentTypeService,
        PaymentPlanService
    ]
})
export class ColegioModule { }
