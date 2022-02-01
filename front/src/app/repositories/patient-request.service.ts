import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import { Patient } from '../model/patient';
import {catchError, retry} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})


export class PatientRequestService  {

  urlApi = 'http://localhost:8080/patient';  // URL de l'API

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

  getPatient(id: number): Observable<HttpResponse<Patient>> {
      return this.http.get<HttpResponse<Patient>>(this.urlApi + '/' + id)
        .pipe();
    }

  getAgePatient(id: number): Observable<number> {
    return this.http.get<number>(this.urlApi + '/calculateAge/' + id, )
      .pipe();
  }

  getCalculatePatientRisk(id: number): Observable<any> {
    return this.http.get(this.urlApi + '/calculateRisk/' + id, { responseType: 'text' })
      .pipe();
  }

  getCalculatePatientRiskWhithoutDouble(id: number): Observable<any> {
    return this.http.get(this.urlApi + '/calulateRiskWithoutDouble/' + id, { responseType: 'text' })
      .pipe();
  }

  searchPatient(patient: Patient): Observable<HttpResponse<Patient>> {
    return this.http.post<HttpResponse<Patient>>(this.urlApi + '/search', patient);
  }

  addNewPatient(patient: Patient): Observable<HttpResponse<Patient>> {
  return this.http.post<HttpResponse<Patient>>(this.urlApi + '/save', patient, this.httpOptions);
  }

  deletePatient(id: number): Observable<number> {
    // @ts-ignore
    return this.http.delete(this.urlApi + '/delete/' + id, this.httpOptions)
      .pipe(
        retry(1),
        catchError(this.handleError)
      );
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




