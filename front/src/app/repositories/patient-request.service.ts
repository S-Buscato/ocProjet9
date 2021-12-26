import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import { Patient } from '../model/patient';
import {catchError, retry} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})


export class PatientRequestService  {

  urlApi = 'http://localhost:9002/patient';  // URL de l'API

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json'
    })
  };

  constructor(private http: HttpClient) {}


    getAllPatient(): Observable<Patient[]> {
      return this.http.get<Patient[]>(this.urlApi)
        .pipe();
      }

  getPatient(id: number): Observable<Patient> {
      return this.http.get<Patient>(this.urlApi + '/' + id)
        .pipe();
    }

    addNewPatient(patient: Patient): Observable<Patient> {
      // @ts-ignore
    return this.http.post<Patient>(this.urlApi + '/save', patient, this.httpOptions)
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



