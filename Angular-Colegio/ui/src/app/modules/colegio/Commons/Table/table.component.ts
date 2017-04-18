import { Component, Input, Output, OnInit, 
         OnDestroy, EventEmitter, ViewChild, ElementRef } from '@angular/core';
import { TableModel } from './table.model.component';
import { ColumnModel } from './column.model.component';

@Component({
  selector: 'select-one-menu-commons',
  templateUrl: './src/app/modules/colegio/Commons/Table/table.component.html',
  styleUrls: ['./src/app/modules/colegio/Commons/Table/table.component.css'],
})
export class TableCommonsComponent implements OnInit{

  @Input()
  tableModel: TableModel;
  @Output() 
  valueModel = new EventEmitter();

  isActive: boolean;
  isErrorEmpty: boolean;

  constructor() {}

  ngOnInit(){

  }
  
}