 import { Component, Input, OnInit, OnDestroy, ViewChild} from '@angular/core';
import { Course } from '../../Models/Course.model';
import { I18n } from '../../../../i18n/i18n';
import { Logger } from '../../../../logger/logger';
import {Header} from 'primeng/primeng';
import {Footer} from 'primeng/primeng';
import { InputCommonsComponent } from '../../Commons/Input/input.component';
import { InputModel } from '../../Commons/Input/input.model.component';
import { SelectItem } from 'primeng/primeng';

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
  filterLevel: string;
  filterLevelsOptions: SelectItem[];
  filterYear: string;
  filterYearOptions: SelectItem[];
  filterDivision: string;
  filterDivisionOptions: SelectItem[];
  
  private language: string;

  constructor() {}

  ngOnInit() {

    this.language = "ES";
    this.selectedCourse = new Course();
    this.courses = new Array<Course>();
    this.cargarCourseTest();
    this.inListCourse = true;
    this.inCreateCourse = false;
    this.inUpdateCourse = false;
    this.morecourses = true;
    this.loading = false;
    this.filterLevelsOptions = [];
    this.filterLevelsOptions.push({label:'Nivel', value:null});
    this.filterLevelsOptions.push({label:'Inicial', value:"inicial"});
    this.filterLevelsOptions.push({label:'Primario', value:"primario"});
    this.filterLevelsOptions.push({label:'Secundario', value:"secundario"});
    this.filterYearOptions = [];
    this.filterYearOptions.push({label:'Año', value:null});
    this.filterYearOptions.push({label:'1°', value:1});
    this.filterYearOptions.push({label:'2°', value:2});
    this.filterYearOptions.push({label:'3°', value:3});
    this.filterYearOptions.push({label:'4°', value:4});
    this.filterYearOptions.push({label:'5°', value:5});
    this.filterYearOptions.push({label:'6°', value:6});
    this.filterYearOptions.push({label:'7°', value:7});
    this.filterDivisionOptions = [];
    this.filterDivisionOptions.push({label:'Division', value:null});
    this.filterDivisionOptions.push({label:'A', value:"A"});
    this.filterDivisionOptions.push({label:'B', value:"B"});
    this.filterDivisionOptions.push({label:'C', value:"C"});
    this.filterDivisionOptions.push({label:'D', value:"D"});
    this.filterDivisionOptions.push({label:'E', value:"E"});
    //this.cargarcourses();

  }

  protected fetchNextChunk(skip: number, limit: number): Promise<Course[]> {
      return new Promise((resolve, reject) => {
          new Array<Course>();
      });
  }

  
  cargarCourseTest(){

    let course: Course = new Course();
    course.division = "Mercantil";
    course.level = "SECUNDARIO";
    course.year = "3";
    course.description = "";
    course.id = 1;

    let course2: Course = new Course();
    course2.division = "CBU";
    course2.level = "SECUNDARIO";
    course2.year = "2";
    course.description = "";
    course.id = 2;
    
    let course3: Course = new Course();
    course3.division = "Mercantil";
    course3.level = "SECUNDARIO";
    course3.year = "2";
    course.description = "";
    course.id = 3;

    let course4: Course = new Course();
    course4.division = "Mercantil";
    course4.level = "SECUNDARIO";
    course4.year = "1";
    course.description = "";
    course.id = 4;

    let course5: Course = new Course();
    course5.division = "Verde";
    course5.level = "INICIAL";
    course5.year = "2";
    course.description = "";
    course.id = 5;

    this.courses = [];
    this.courses.push(course);
    this.courses.push(course2);
    this.courses.push(course3);
    this.courses.push(course4);
    this.courses.push(course5);
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
      this.cargarCourseTest();
  }

  loadCourse(course: Course){
    this.selectedCourse = course;
    this.inUpdateCourse = true;
    this.inListCourse = false;
    this.inCreateCourse = false;
  }

}