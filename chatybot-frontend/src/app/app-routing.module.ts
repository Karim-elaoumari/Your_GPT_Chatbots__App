import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './view/pages/home/home.component';
import { LoginComponent } from './view/pages/login/login.component';
import { RegisterComponent } from './view/pages/register/register.component';
import { ChatbotsComponent } from './view/pages/chatbots/chatbots.component';
import { AuthGuard } from './core/guards/auth.guard';
import { RolesGuard } from './core/guards/roles.guard';
import { ChatTemplateComponent } from './view/pages/chat-template/chat-template.component';

const routes: Routes = [
  { path: '',   redirectTo: '/home', pathMatch: 'full'},
  {path:'home', component: HomeComponent},
  {path: 'login',component: LoginComponent},
  { path: 'register', component: RegisterComponent},
  { path: 'chatbots',component: ChatbotsComponent,canActivate: [AuthGuard,RolesGuard],data: {expectedRoles: ['ADMIN','USER']}},
  { path: 'chat-template',component: ChatTemplateComponent}
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { 

}
