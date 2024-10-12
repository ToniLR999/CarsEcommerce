import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import {provideHttpClient } from '@angular/common/http';  // Asegúrate de importar esto

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component'; // Tu componente
import { AdminPanelComponent } from './admin-panel/admin-panel.component';
import { AdminPanelSectionComponent } from './admin-panel-section/admin-panel-section.component';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    AdminPanelComponent,
    AdminPanelSectionComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [provideHttpClient()],
  bootstrap: [AppComponent]
})
export class AppModule { }
