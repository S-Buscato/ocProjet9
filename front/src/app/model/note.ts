import {Patient} from './patient';

export interface INote{
  id?: number;
  createdDate?: string;
  updatedDate?: string;
  patientId?: number;
  note?: string;
}

export class Note implements INote {
  constructor(
    public id?: number,
    public createdDate?: string,
    public updatedDate?: string,
    public patientId?: number,
    public note?: string
  ) {}

}
