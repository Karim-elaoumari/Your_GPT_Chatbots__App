import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './view/pages/home/home.component';
import { LoginComponent } from './view/pages/login/login.component';
import { RegisterComponent } from './view/pages/register/register.component';
import { ChatbotsComponent } from './view/pages/chatbots/chatbots.component';
import { AuthGuard } from './core/guards/auth.guard';
import { RolesGuard } from './core/guards/roles.guard';
import { ChatTemplateComponent } from './view/pages/chat-template/chat-template.component';
import { ChatbotsListComponent } from './view/pages/dash-includes/chatbots-list/chatbots-list.component';
import { ChatbotDetailsComponent } from './view/pages/dash-includes/chatbot-details/chatbot-details.component';
import { AccountComponent } from './view/pages/dash-includes/account/account.component';
import { NewChatbotComponent } from './view/pages/dash-includes/new-chatbot/new-chatbot.component';
import { RouteNotFoundPageComponent } from './view/pages/route-not-found-page/route-not-found-page.component';
import { InternalServerErrorComponent } from './view/pages/internal-server-error/internal-server-error.component';

const routes: Routes = [
  { path: '',   redirectTo: '/home', pathMatch: 'full'},
  {path:'home', component: HomeComponent},
  {path: 'login',component: LoginComponent},
  { path: 'register', component: RegisterComponent},
  { path: 'chatbots',component: ChatbotsComponent, canActivate: [AuthGuard,RolesGuard],data: {expectedRoles: ['ADMIN','USER']} ,children: [
    { path: 'chatbots-list',component: ChatbotsListComponent},
    { path: 'chatbot-details/:ch',component: ChatbotDetailsComponent},
    { path: 'account',component: AccountComponent},
    { path: 'new-chatbot',component: NewChatbotComponent},
    { path: '', redirectTo: 'chatbots-list', pathMatch: 'full'}
  ]},
  { path: 'chat-template',component: ChatTemplateComponent},
  { path: 'not-found',component: RouteNotFoundPageComponent},
  { path: 'server-error',component: InternalServerErrorComponent},
  { path: '**', redirectTo: '/not-found', pathMatch: 'full'},
  
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { 

}
