import {  NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import { rootEffects } from './state/root.effect';
import { rootReducers } from './state/root.reducer';
import { AppRoutingModule } from './app-routing.module';
import { PagesModule } from './view/pages/pages.module';
import { BrowserModule } from '@angular/platform-browser';
import {  HttpClientModule } from '@angular/common/http';
import { IncludesModule } from './view/includes/includes.module';
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
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule { }
