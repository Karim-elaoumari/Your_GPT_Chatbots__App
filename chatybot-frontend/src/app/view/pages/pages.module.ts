import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '../shared/shared.module';
import { IncludesModule } from '../includes/includes.module';
import { ChatbotsComponent } from './chatbots/chatbots.component';
import { ChatTemplateComponent } from './chat-template/chat-template.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ChatbotsListComponent } from './dash-includes/chatbots-list/chatbots-list.component';
import { ChatbotDetailsComponent } from './dash-includes/chatbot-details/chatbot-details.component';
import { AccountComponent } from './dash-includes/account/account.component';
import { NewChatbotComponent } from './dash-includes/new-chatbot/new-chatbot.component';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { ChatbotDashboardComponent } from './dash-includes/chatbot-dashboard/chatbot-dashboard.component';
import { ChatbotDataComponent } from './dash-includes/chatbot-data/chatbot-data.component';
import { ChatbotChatbotComponent } from './dash-includes/chatbot-chatbot/chatbot-chatbot.component';
import { ChatbotSettingsComponent } from './dash-includes/chatbot-settings/chatbot-settings.component';
import { ChatbotLogComponent } from './dash-includes/chatbot-log/chatbot-log.component';
import { ClipboardModule } from '@angular/cdk/clipboard';
import { SafeUrlPipe } from 'src/app/view/pipes/safe-url.pipe';
import { RouteNotFoundPageComponent } from './route-not-found-page/route-not-found-page.component';
import { InternalServerErrorComponent } from './internal-server-error/internal-server-error.component';



@NgModule({
  declarations: [
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    ChatbotsComponent,
    ChatTemplateComponent,
    ChatbotsListComponent,
    ChatbotDetailsComponent,
    AccountComponent,
    NewChatbotComponent,
    ChatbotDashboardComponent,
    ChatbotDataComponent,
    ChatbotChatbotComponent,
    ChatbotSettingsComponent,
    ChatbotLogComponent,
    SafeUrlPipe,
    RouteNotFoundPageComponent,
    InternalServerErrorComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    SharedModule,
    IncludesModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    ClipboardModule,
  ],
  exports: [
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    ChatbotsComponent,
    ChatTemplateComponent,
    ChatbotsListComponent,
    ChatbotDetailsComponent,
    AccountComponent,
    NewChatbotComponent,
    RouteNotFoundPageComponent,
    InternalServerErrorComponent
    
  ],
})
export class PagesModule { }
