import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PatientComponent } from './patient/patient.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import {AddUpdatePatientComponent} from './add-update-patient/add-update-patient.component';
import { HttpClientModule } from '@angular/common/http';




@NgModule({
  declarations: [PatientComponent, AddUpdatePatientComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    BrowserModule,
    HttpClientModule
  ]
})
export class PatientModule {}
