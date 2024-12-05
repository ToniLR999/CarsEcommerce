import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import {provideHttpClient } from '@angular/common/http';  // Asegúrate de importar esto

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component'; // Tu componente
import { AdminPanelComponent } from './admin-panel/admin-panel.component';
import { AdminPanelSectionComponent } from './admin-panel-section/admin-panel-section.component';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatOptionModule } from '@angular/material/core'; // Opcional, a veces se importa directamente en MatSelectModule

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    AdminPanelComponent,
    AdminPanelSectionComponent
  ],
  imports: [
    MatSelectModule,
    MatOptionModule,
    MatFormFieldModule,
    BrowserAnimationsModule,
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [provideHttpClient()],
  bootstrap: [AppComponent]
})
export class AppModule { }
