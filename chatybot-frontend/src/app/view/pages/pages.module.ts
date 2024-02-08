import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home/home.component';
import { IncludesModule } from '../includes/includes.module';



@NgModule({
  declarations: [
    HomeComponent
  ],
  imports: [
    CommonModule,
  ],
  exports: [
    HomeComponent,
  ],
})
export class PagesModule { }
