import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NoteListComponent } from './note-list/note-list.component';
import {BrowserModule} from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http';
import {RouterModule} from '@angular/router';
import { AddUpdateNoteComponent } from './add-update-note/add-update-note.component';
import {ReactiveFormsModule} from '@angular/forms';



@NgModule({
  declarations: [
    NoteListComponent,
    AddUpdateNoteComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    HttpClientModule,
    RouterModule,
    ReactiveFormsModule
  ]
})
export class NoteModule { }
