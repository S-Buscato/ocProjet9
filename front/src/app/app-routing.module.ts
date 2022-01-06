import {NgModule} from '@angular/core';
import {Routes, RouterModule } from '@angular/router';
import {PatientComponent} from './patient/patient/patient.component';
import {AddUpdatePatientComponent} from './patient/add-update-patient/add-update-patient.component';
import {HomeComponent} from './home/home.component';
import {PatientDetailComponent} from './patient/patient-detail/patient-detail.component';
import {PatientSearchComponent} from './patient/patient-search/patient-search.component';
import {NoteListComponent} from './note/note-list/note-list.component';
import {AddUpdateNoteComponent} from './note/add-update-note/add-update-note.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'patients', component: PatientComponent },
  { path: 'search', component: PatientSearchComponent},
  { path: 'patients/add', component: AddUpdatePatientComponent},
  { path: 'patients/add/:id', component: AddUpdatePatientComponent},
  { path: 'patients/:id', component: PatientDetailComponent},
  { path: 'patients/note/:id', component: NoteListComponent},
  { path: 'note/add/:idPatient', component: AddUpdateNoteComponent},
  { path: 'note/add/:idPatient/:idNote', component: AddUpdateNoteComponent}


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
