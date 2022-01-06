import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {NoteRequestService} from '../../repositories/note-request.service';
import {Note} from '../../model/note';


@Component({
  selector: 'app-add-update-note',
  templateUrl: './add-update-note.component.html',
  styleUrls: ['./add-update-note.component.css']
})
export class AddUpdateNoteComponent implements OnInit {
  @Input()
  noteform: FormGroup;

  public note: Note;
  public idNote: number;
  public idPatient: number;

  constructor( private route: ActivatedRoute,
               private fb: FormBuilder,
               private noteRequestService: NoteRequestService,
               private router: Router) { }

  ngOnInit(): void {
    this.idPatient = this.route.snapshot.params.idPatient;
    if (this.route.snapshot.params.idNote != null) {
      this.idNote = this.route.snapshot.params.idNote;
      this.noteRequestService.getNote(this.idNote).subscribe(
        data => {
          this.note = data;
          this.noteform = this.fb.group({
            note: new FormControl(this.note.note, Validators.required),
          });
        }
      );
    } else {
      this.noteform = this.fb.group({
        note: new FormControl('', Validators.required),
      });
    }
  }

  onFormSubmit(): void {
    const note: Note = new Note();
    note.id = this.idNote || 0 ;
    note.patientId = this.idPatient;
    note.note = this.noteform.controls.note.value;
    if (this.note !== undefined) {
      note.createdDate = this.note.createdDate;
      note.updatedDate = this.note.updatedDate;
    }
    try {
      this.noteRequestService.addNewPatientNote(note).subscribe( data => {
        // @ts-ignore
        if (data){
          // @ts-ignore
          this.router.navigate(['patients/note/' + this.idPatient], { replaceUrl: this.route });
        }
      });
    }catch (error){
      console.log('save err', error);
    }
  }

  // tslint:disable-next-line:typedef
  onCancelMaj() {
    this.router.navigate(['/patients/note/' + this.idPatient]);
  }

  // tslint:disable-next-line:typedef
  public deleteNote() {
    if (confirm("voulez-vous vraiment supprimer cette note patient ?")) {
      // @ts-ignore
      this.noteRequestService.deletePatientNote(this.note).subscribe(
        data => {
          if (data) {
            // @ts-ignore
            this.router.navigate(['patients/note/' + this.idPatient], { replaceUrl: this.route });
          }
        }
      );
    }
  }
}
