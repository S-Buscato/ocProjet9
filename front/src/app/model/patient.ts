export interface IPatient{
    id?: number;
    firstname?: string;
    lastname?: string;
    dob?: string;
    sex?: string;
    phone?: string;
    address?: string;
}

export class Patient implements IPatient {
    constructor(
        public id?: number,
        public firstname?: string,
        public lastname?: string,
        public dob?: string,
        public sex?: string,
        public phone?: string,
        public address?: string
        ) {}

}
