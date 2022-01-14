import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {Patient} from '../model/patient';
import {catchError, retry} from 'rxjs/operators';
import {Note} from '../model/note';

@Injectable({
  providedIn: 'root'
})
export class NoteRequestService {

  urlApi = 'http://localhost:8080/note';  // URL de l'API

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json'
    })
  };

  constructor(private http: HttpClient) {}

  getNote(id: number): Observable<Note> {
    return this.http.get<Note>(this.urlApi + '/' + id)
      .pipe();
  }

  getPatientNote(id: number): Observable<Note[]> {
    return this.http.get<Note[]>(this.urlApi + '/search/' + id)
      .pipe();
  }

  searchPatient(patient: Patient): Observable<Patient> {
    return this.http.post<Patient>(this.urlApi + '/search', patient)
      .pipe(retry(1),
        catchError(this.handleError)
      );
  }

  addNewPatientNote(note: Note): Observable<Note> {
    // @ts-ignore
    return this.http.post<Note>(this.urlApi + '/save', note, this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.handleError)
      )/*.subscribe(
      res => console.log('HTTP response', res),
      err => console.log('HTTP Error', err),
  () => console.log('HTTP request completed.')
    );*/
  }

  deletePatientNote(note: Note): Observable<Note> {
    // @ts-ignore
    return this.http.post<Note>(this.urlApi + '/delete', note, this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.handleError)
      )/*.subscribe(
        res => console.log('HTTP response', res),
        err => console.log('HTTP Error', err),
    () => console.log('HTTP request completed.')
      );*/
  }

  deleteAllPatientNote(patientId: number): Observable<number> {
    // @ts-ignore
    return this.http.post<number>(this.urlApi + '/deleteAll/' + patientId)
      .pipe(
        retry(1),
        catchError(this.handleError)
      )/*.subscribe(
        res => console.log('HTTP response', res),
        err => console.log('HTTP Error', err),
    () => console.log('HTTP request completed.')
      );*/
  }


  // tslint:disable-next-line:typedef no-shadowed-variable
  handleError(error) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // client-side error
      errorMessage = `Error: ${error.error.message}`;
    } else {
      console.log('err ? ', error.error);
      // server-side error
      /*errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;*/
      errorMessage = error.error;
    }
    return throwError(errorMessage);
  }
}
