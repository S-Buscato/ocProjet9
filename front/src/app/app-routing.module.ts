import {NgModule} from '@angular/core';
import {Routes, RouterModule } from '@angular/router';
import {PatientComponent} from './patient/patient/patient.component';
import {AddUpdatePatientComponent} from './patient/add-update-patient/add-update-patient.component';
import {HomeComponent} from './home/home.component';
import {PatientDetailComponent} from './patient/patient-detail/patient-detail.component';
import {PatientSearchComponent} from './patient/patient-search/patient-search.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'patients', component: PatientComponent },
  { path: 'search', component: PatientSearchComponent},
  { path: 'patients/add', component: AddUpdatePatientComponent},
  { path: 'patients/add/:id', component: AddUpdatePatientComponent},
  { path: 'patients/:id', component: PatientDetailComponent}


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
