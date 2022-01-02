import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  constructor() { }

  // tslint:disable-next-line:typedef
  public capitalize(word) {
    return word[0].toUpperCase() + word.slice(1).toLowerCase();
  }
}
