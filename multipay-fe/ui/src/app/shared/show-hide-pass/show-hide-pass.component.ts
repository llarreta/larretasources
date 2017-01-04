import {Component, ContentChild} from '@angular/core';

@Component({ selector: 'show-hide-container',
            template: `<ng-content></ng-content>
                       <i class="input-show-pass" (click)="toggleShow($event)"></i>`
})
export class ShowHidePass 
{
    show = false;
    
    @ContentChild('showhideinput') input;
    
    constructor(){}
   
    toggleShow()
    {
        this.show = !this.show;
        if (this.show){
            this.input.nativeElement.type='text';
        }
        else {
            this.input.nativeElement.type='password';
        }
    }
}