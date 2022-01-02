import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PatientComponent } from './patient/patient.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import {AddUpdatePatientComponent} from './add-update-patient/add-update-patient.component';
import { HttpClientModule } from '@angular/common/http';
import { PatientDetailComponent } from './patient-detail/patient-detail.component';
import {RouterModule} from '@angular/router';
import {PatientSearchComponent} from './patient-search/patient-search.component';


@NgModule({
  declarations: [
    PatientComponent,
    AddUpdatePatientComponent,
    PatientDetailComponent,
    PatientSearchComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    BrowserModule,
    HttpClientModule,
    RouterModule
  ]
})
export class PatientModule {}
