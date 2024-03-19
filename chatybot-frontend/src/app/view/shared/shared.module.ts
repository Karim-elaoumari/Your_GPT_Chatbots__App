import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AlertComponent } from './alert/alert.component';
import { Loader1Component } from './loader1/loader1.component';
import { PaginationComponent } from './pagination/pagination.component';
import { ChatStaticTemplateComponent } from './chat-static-template/chat-static-template.component';
import { SpinnerComponent } from './spinner/spinner.component';



@NgModule({
  declarations: [
  
    AlertComponent,
       Loader1Component,
       PaginationComponent,
       ChatStaticTemplateComponent,
       SpinnerComponent,
  ],
  imports: [
    CommonModule
  ],
  exports: [
    AlertComponent,
    Loader1Component,
    PaginationComponent,
    ChatStaticTemplateComponent,
    SpinnerComponent,
  ]
})
export class SharedModule { }
