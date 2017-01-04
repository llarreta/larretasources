import {
    Input,
    Output,
    Component,
    ElementRef,
    EventEmitter
} from '@angular/core';
/**
 * Example of Usage:
 
  # Template:  

  <dropdown-select 
  [default-item]="default" 
  [items]="list" 
  (selectChanged)="selected($event)"></dropdown-select>

  <dropdown-select 
  [default-item]="defaultText" 
  [items]="listText" 
  (selectChanged)="selected($event)"></dropdown-select>

  # Component:  

  private default = {id: -1, text: "Select a value"};
  private list = [{id: 1, text: "Test"},{id: 2, text: "Test 2"}];

  private defaultText = "Select a value";
  private listText = ["Value 1", "Value 2", "Value 3"];

  selected(el: any){
    console.log(el);
  }

 */
@Component({
    selector: 'dropdown-select',
    templateUrl: './src/app/shared/dropdown-select/dropdown-select.component.html',
})
export class DropdownSelectComponent {

    private list;
    private defaultElement: any;
    private selectedElement: any;


    @Input('default-item')
    set defaultItem(defaultItem: any) {
        this.defaultElement = defaultItem;
        this.selectedElement = defaultItem;
    }

    @Input()
    set items(items: Array<any>) {
        this.list = items;
    }

    @Output()
    private selectChanged = new EventEmitter();

    selectElement(index: number) {
        if (index == -1) {
            this.selectedElement = this.defaultElement;
        } else {
            this.selectedElement = this.list[index];
        }
        this.selectChanged.emit(this.selectedElement);
    }

    showValue(elem: any) {
        let value;
        if (typeof elem == "object") {
            value = elem.text;
        } else {
            value = elem;
        }
        return value;
    }
}