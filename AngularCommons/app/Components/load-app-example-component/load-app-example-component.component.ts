import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  moduleId: module.id,
  selector: 'load-app-example-component-common',
  templateUrl: 'load-app-example-component.html',
  styleUrls: ['load-app-example-component.css']
})

export class LoadAppExampleComponent implements OnInit {

  
  constructor(
    private router: Router) {
  }
  
  ngOnInit(){
     
  }

  resizeIframe(iframe){
    console.log("hola");
    var iframe2 =  (<HTMLIFrameElement>document.getElementById("otherApp")).contentWindow.document.getElementsByTagName('body');
    //.document.body.scrollHeight + 'px';
    //console.log(iframe2);
  }

}
