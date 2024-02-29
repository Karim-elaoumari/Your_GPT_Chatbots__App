import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { FormsModule } from '@angular/forms';
import { SharedModule } from '../shared/shared.module';
import { IncludesModule } from '../includes/includes.module';
import { ChatbotsComponent } from './chatbots/chatbots.component';
import { ChatTemplateComponent } from './chat-template/chat-template.component';



@NgModule({
  declarations: [
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    ChatbotsComponent,
    ChatTemplateComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    SharedModule,
    IncludesModule
  ],
  exports: [
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    ChatbotsComponent,
    ChatTemplateComponent
  ],
})
export class PagesModule { }
