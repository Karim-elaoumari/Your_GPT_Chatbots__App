import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './view/pages/home/home.component';
import { LoginComponent } from './view/pages/login/login.component';
import { RegisterComponent } from './view/pages/register/register.component';
import { UserInfoResolver } from './core/resolvers/UserInfoResolver';
import { ChatbotsComponent } from './view/pages/chatbots/chatbots.component';
import { AuthGuard } from './core/guards/auth.guard';
import { RolesGuard } from './core/guards/roles.guard';

const routes: Routes = [
  { path: '',   redirectTo: '/home', pathMatch: 'full'},
  {path:'home', component: HomeComponent,resolve: {userInfo: UserInfoResolver}},
  {path: 'login',component: LoginComponent},
  { path: 'register', component: RegisterComponent},
  { path: 'chatbots',component: ChatbotsComponent,resolve: {userInfo: UserInfoResolver},canActivate: [AuthGuard,RolesGuard],data: {expectedRoles: ['ADMIN','USER']}},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { 

}
