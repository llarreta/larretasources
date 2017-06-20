//Angular
import { Component, Input, OnInit, OnDestroy, ViewChild} from '@angular/core';

//ngPrime
import { SelectItem } from 'primeng/primeng';
import {Header} from 'primeng/primeng';
import {Footer} from 'primeng/primeng';

//Commons
import { ErrorMessages } from '../../../../ErrorMessages/ErrorMessages';
import { InputCommonsComponent } from '../../Commons/Input/input.component';
import { InputModel } from '../../Commons/Input/input.model.component';
import { I18n } from '../../../../i18n/i18n';
import { Logger } from '../../../../logger/logger';

//Models
import { Division } from '../../Models/Division.model';
import { Level } from '../../Models/Level.model';
import { Year } from '../../Models/Year.model';
import { Course } from '../../Models/Course.model';

//Services
import { LevelService } from '../../services/level.service';
import { YearService } from '../../services/year.service';
import { DivisionService } from '../../services/division.service';
import { CourseService } from '../../services/course.service';

@Component({
  selector: 'school-courses',
  templateUrl: './src/app/modules/colegio/Course/List/Course.component.html'
})
export class CourseComponent implements OnInit{

  courses: Array<Course>;
  selectedCourse: Course;
  inCreateCourse: boolean;
  inUpdateCourse: boolean;
  inListCourse: boolean;
  loading: boolean;
  morecourses: boolean;
  filterLevel: Level;
  filterLevelsOptions: Array<SelectItem>;
  filterYear: Year;
  filterYearOptions: Array<SelectItem>;
  filterDivision: Division;
  filterDivisionOptions: Array<SelectItem>;

  showMessageError: boolean;
  showMessageErrorService: boolean; 
  messageErrorService: string;
  
  displayLoading: string;

  private language: string;

  constructor(private courseService: CourseService, private levelService: LevelService,
              private yearService: YearService, private divisionService: DivisionService) {}

  ngOnInit() {
    this.language = "ES";
    this.selectedCourse = new Course();
    this.courses = new Array<Course>();
    this.loadCourses();
    this.inListCourse = true;
    this.inCreateCourse = false;
    this.inUpdateCourse = false;
    this.morecourses = true;
    this.loading = false;
    this.loadInitData();
  }

  private loadInitData(){
    this.showLoading();
    this.loadDivisions();
  }

  loadDivisions(){
    this.divisionService.loadDivisions()
       .subscribe(
        data => this.loadDivisionsOK(data),
        err => this.loadErrorMessageService(err),
        () => console.log('Vacio')
    );
  }

  loadDivisionsOK(data){
    this.filterDivisionOptions = new Array<SelectItem>();
    this.filterDivisionOptions.push({label:"Seleccionar Division", value:null});
    for(let divisionJSON of data.body.result){
      let division: Division = new Division();
      Object.assign(division, divisionJSON);
      this.filterDivisionOptions.push({label:division.description, value:division});
    }
    this.loadLevels();
  }

  loadLevels(){
    this.showLoading();
    this.levelService.loadLevels()
       .subscribe(
        data => this.loadLevelsOK(data),
        err => this.loadErrorMessageService(err),
        () => console.log('Vacio')
    );
  }

  loadLevelsOK(data){
    this.filterLevelsOptions = new Array<SelectItem>();
    this.filterLevelsOptions.push({label:"Seleccionar Nivel", value:null});
    for(let levelJSON of data.body.result){
      let level: Level = new Level();
      Object.assign(level, levelJSON);
      this.filterLevelsOptions.push({label:level.description, value:level});
    }
    this.loadYears();
  }

  loadYears(){
    this.showLoading();
    this.yearService.loadYears()
       .subscribe(
        data => this.loadYearsOK(data),
        err => this.loadErrorMessageService(err),
        () => console.log('Vacio')
    );
  }

  loadYearsOK(data){
    this.filterYearOptions = new Array<SelectItem>();
    this.filterYearOptions.push({label:"Seleccionar Año", value:null});
    for(let yearJSON of data.body.result){
      let year: Year = new Year();
      Object.assign(year, yearJSON);
      this.filterYearOptions.push({label:year.description, value:year});
    }
    this.hideLoading();
  }

  protected fetchNextChunk(skip: number, limit: number): Promise<Course[]> {
      return new Promise((resolve, reject) => {
          new Array<Course>();
      });
  }

  goListCreate(goList: boolean) {
    this.inUpdateCourse = false;
    this.inCreateCourse = !goList;
    this.inListCourse = goList;
  }

  goListUpdate(goList: boolean){
    this.inCreateCourse = false;
    this.inUpdateCourse = !goList;
    this.inListCourse = goList;
  }

  loadData(event) {
      
  }

  loadCourses(){
    this.courseService.loadCourses()
       .subscribe(
        data => this.loadCoursesOK(data),
        err => this.loadErrorMessageService(err),
        () => console.log('Vacio')
      );
  }

  loadCoursesOK(data){
    
  }

  loadErrorMessageService(error){
    Logger.warn("Ocurrio un error al crear un curso...");
    this.messageErrorService = ErrorMessages.getMessageError(error.codeError, "ES");
    this.showMessageErrorService = true;
    this.showMessageError = true;
    this.hideLoading();
  }

  loadCourse(course: Course){
    this.selectedCourse = course;
    this.inUpdateCourse = true;
    this.inListCourse = false;
    this.inCreateCourse = false;
  }

  showLoading(){
    this.displayLoading = "block";
  }

  hideLoading(){
    this.displayLoading = "none";
  }

}