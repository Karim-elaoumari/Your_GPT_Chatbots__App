import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AlertComponent } from './alert/alert.component';
import { Loader1Component } from './loader1/loader1.component';



@NgModule({
  declarations: [
  
    AlertComponent,
       Loader1Component
  ],
  imports: [
    CommonModule
  ],
  exports: [
    AlertComponent,
    Loader1Component
  ]
})
export class SharedModule { }
