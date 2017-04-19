import { Component, Input, Output, OnInit, 
         OnDestroy, EventEmitter, ViewChild, ElementRef } from '@angular/core';
import { TableModel } from './table.model.component';
import { ColumnModel } from './column.model.component';
import { ActionButtonTable } from './actionButtonTable.model.component';
import { Logger } from '../../../../Logger/logger';
import { InputModel } from '../Input/input.model.component';

@Component({
  selector: 'table-commons',
  templateUrl: './src/app/modules/colegio/Commons/Table/table.component.html',
  styleUrls: ['./src/app/modules/colegio/Commons/Table/table.component.css'],
})
export class TableCommonsComponent implements OnInit{

  @Input()
  tableModel: TableModel;
  @Output() 
  action = new EventEmitter();

  isActive: boolean;
  isErrorEmpty: boolean;
  valuesForView: Array<Array<Array<string>>>;
  actualPage: number;
  inputGlobalFilter: InputModel;

  constructor() {}

  ngOnInit(){
    this.actualPage = 0;
    this.loadValuesForView();
    this.inputGlobalFilter = new InputModel();
    this.inputGlobalFilter.labelContent = "Buscar...";
    this.inputGlobalFilter.type = "text";
    this.inputGlobalFilter.validationActivate = false;
  }

  loadValuesForView(){
    Logger.info("Creando tabla...");
    Logger.info("loadValuesForView...");
    Logger.debug("Estado de valores...");
    this.valuesForView = new Array<Array<Array<string>>>();
    Logger.debug(this.tableModel.values);
    Logger.debug("Paginator: " + this.tableModel.paginator + " ...");
      
    let page: Array<Array<string>> = new Array<Array<string>>();
    let counterRows: number = 0;

    for(let value of this.tableModel.values){
      
      let row: Array<string> = new Array<string>();
      let valueJSON = JSON.parse(value);
      row.push(valueJSON["id"]);
      for (let column of this.tableModel.columns) {
        if(!column.columnButton){
          let keys: Array<string> = new Array<string>();
          let valueAux;
          
          Logger.debug("keys columna...");
          Logger.debug(column.key);
          keys = this.filterKeys(column.key);
          Logger.debug("keys procesadas...");
          Logger.debug(keys);
          for(let i = 0; i < keys.length; i++){
              if(i == 0){
                valueAux = valueJSON[keys[i]];
              }else{
                valueAux = valueAux[keys[i]];
              }
          }
          Logger.debug("valueAux...");
          Logger.debug(valueAux);

          row.push(valueAux);
        }else{
          row.push("button");
        }
      }

      page.push(row);
      counterRows += 1;

      Logger.debug("row...");
      Logger.debug(row);
      if((this.tableModel.paginator != null) && (this.tableModel.paginator >= 1) 
          && (counterRows >= this.tableModel.paginator)){
        this.valuesForView.push(page);
        counterRows = 0;
        page = new Array<Array<string>>();
      }
    }
      if(counterRows >= 1 && counterRows < this.tableModel.paginator){
        this.valuesForView.push(page);
      }
      if((this.tableModel.paginator == null) || (this.tableModel.paginator < 1)){
        this.valuesForView.push(page);
      }
      Logger.debug("valuesForView...");
      Logger.debug(this.valuesForView);
  }

  filterKeys(key: string){
    let keysAux: string[] = key.split(".");
    let keys: Array<string> = new Array<string>();
    keys.push(...keysAux);
    return keys;
  }

  buttonClick(action, id){
    let actionButtonTable: ActionButtonTable = new ActionButtonTable();
    actionButtonTable.action = action;
    actionButtonTable.value = id;
    this.action.emit(actionButtonTable);
  }

  changePage(page){
    this.actualPage = page;
  }

  setGlobalFilter(inputModel: InputModel){
    this.inputGlobalFilter = inputModel;
    Logger.debug("Global filter...")
  }
  
}