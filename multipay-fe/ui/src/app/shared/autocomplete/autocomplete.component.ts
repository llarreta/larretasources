import {
    Input,
    Output,
    Component,
    ElementRef,
    EventEmitter
} from '@angular/core';

@Component({
    selector: 'autocomplete',
    host: {
        '(document:click)': 'handleClick($event)',
        '(keydown)': 'handleKeyDown($event)'
    },
    templateUrl: './src/app/shared/autocomplete/autocomplete.component.html',
})
export class AutocompleteComponent {
    @Input('id')
    private id: string;

    @Input('placeholder')
    private placeholder: string;

    @Input()
    set items(items: Array<Element>) {
        this.filteredList = items;
        this.filteredListAux = items;
    }

    @Output()
    private inputChange = new EventEmitter();

    query: string = '';
    filteredList: Array<Element> = [];
    filteredListAux: Array<Element> = [];
    elementRef: ElementRef;
    pos: number = -1;
    opened: boolean = false;
    selectedItem: any;
    item: any;

    constructor(private el: ElementRef) {
        this.elementRef = el;
    }

    showActiveItem(event: any, show: boolean) {
        let listGroup = document.getElementById(this.id +'-list-group');
        if (listGroup !== undefined) {
            let countChildren = listGroup.children.length;
            if (countChildren > 0) {
                for (let i = 0; i < countChildren; i++) {
                    listGroup.children[i].className = 'list-group-item item-list';
                }
                event.target.className = 'list-group-item item-list';
                if (show) {
                    event.target.className = 'list-group-item item-list active';
                }
            }
        }       
    }

    filterQuery(event: any) {
        if (event.keyCode !== 40 && event.keyCode !== 38 && event.keyCode !== 13) {
            this.pos = -1;
            this.inputChange.emit(this.query);
        }
    }

    filter(event: any) {
        if (this.opened) {
            if (this.query !== '' &&
                ((event.keyCode >= 48 && event.keyCode <= 57) ||
                    (event.keyCode >= 65 && event.keyCode <= 90) ||
                    (event.keyCode == 8))) {
                this.pos = -1;
                this.filterQuery(event);
            }
        } else {
            if (this.query === '') {
                this.pos = -1;
                this.clearFilteredList();
            } else {
                this.filterQuery(event);
            }
        }

        for (let i = 0; i < this.filteredList.length; i++) {
            this.filteredList[i].selected = false;
        }

        if (this.selectedItem) {
            this.filteredList.map((i) => {
                if (i.id == this.selectedItem.id) {
                    this.pos = this.filteredList.indexOf(i);
                }
            })
            this.selectedItem = null;
        }

        // Arrow-key Down
        if (event.keyCode === 40) {
            if (this.pos < this.filteredList.length) {
                this.pos++;
            } 
            if (this.pos >= this.filteredList.length) {
                this.pos = this.filteredList.length - 1;
            }   
        }

        // Arrow-key Up
        if (event.keyCode === 38) {
            if (this.pos > 0) {
                this.pos--;
            } 
            if (this.pos <= 0) {
                this.pos = 0;
            }
        }

        // Back
        if (event.keyCode === 8) {
            this.pos = -1;
        }
        
        if (this.filteredList[this.pos] !== undefined) {
            this.filteredList[this.pos].selected = true;
        }

        //enter
        if (event.keyCode === 13) {
            if (this.filteredListAux[this.pos] !== undefined) {
                this.selectItem(this.filteredListAux[this.pos]);
            }
        }

        // Handle scroll position of item
        let listGroup = document.getElementById(this.id +'-list-group');
        let listItem = document.getElementById('true');
        if (listItem) {
            listGroup.scrollTop = (listItem.offsetTop - 200);
        }

    }

    selectItem(item: any) {
        this.selectedItem = item;
        this.selectedItem.selected = true;
        this.query = item.text;
        this.clearFilteredList();
    }

    clearFilteredList() {
        this.filteredList = [];
    }

    clearAll() {
        if (this.filteredList) {
            for (let i = 0; i < this.filteredList.length; i++)
                this.filteredList[i].selected = false;
        }
    }

    /** Remove selected from all items of the list **/
    clearSelects() {
        if (this.selectedItem) {
            for (let i = 0; i < this.filteredList.length; i++) {
                if (this.filteredList[i].id != this.selectedItem.id)
                    this.filteredList[i].selected = false;
            }
        }
    }

    

    /** Handle outside keydown to close suggestions**/
    handleKeyDown(event: any) {
        // Prevent default actions of arrows or enter
        if (event.keyCode == 40 || event.keyCode == 38 || event.keyCode == 13) {
            event.preventDefault();
        }
    }

    /** Handle outside click to close suggestions**/
    handleClick(event: any) {
        let clickedComponent = event.target;
        let inside = false;
        do {
            if (clickedComponent === this.elementRef.nativeElement) {
                inside = true;
            }
            clickedComponent = clickedComponent.parentNode;
        } while (clickedComponent);
        if (!inside) {
            this.clearFilteredList();
            this.opened = false;
        }
    }
    
}

export interface Element {
    id: number;
    text: string;
    selected: boolean;
}