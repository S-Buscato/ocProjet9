import {ErrorHandler, NgModule} from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {MenuComponent} from './menu/menu.component';
import {PatientComponent} from './patient/patient/patient.component';
import {AddUpdatePatientComponent} from './patient/add-update-patient/add-update-patient.component';
import {HomeComponent} from './home/home.component';
import {PatientDetailComponent} from './patient/patient-detail/patient-detail.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'patient', component: PatientComponent },
  { path: 'patient/add', component: AddUpdatePatientComponent},
  { path: 'patient/add/:id', component: AddUpdatePatientComponent},

  { path: 'patient/:id', component: PatientDetailComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
