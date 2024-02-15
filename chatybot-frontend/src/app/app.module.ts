import {  NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import { rootEffects } from './state/root.effect';
import { rootReducers } from './state/root.reducer';
import { AppRoutingModule } from './app-routing.module';
import { PagesModule } from './view/pages/pages.module';
import { BrowserModule } from '@angular/platform-browser';
import {  HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { IncludesModule } from './view/includes/includes.module';
import { FormsModule } from '@angular/forms';
import { AuthInterceptor } from './core/interceptors/auth.interceptor';
import { CookieService } from 'ngx-cookie-service';
import { EncrDecrService } from './core/services/EncrDecrService';
@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    StoreModule.forRoot(rootReducers),
    EffectsModule.forRoot(rootEffects),
    PagesModule,
    IncludesModule,
    FormsModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    CookieService,
    EncrDecrService
  ],
  bootstrap: [AppComponent],
})
export class AppModule { }
