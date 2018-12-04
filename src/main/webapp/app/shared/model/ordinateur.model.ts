import { Moment } from 'moment';
import { IStagiaire } from 'app/shared/model//stagiaire.model';

export interface IOrdinateur {
    id?: number;
    code?: number;
    coutParJour?: number;
    processeur?: string;
    ram?: number;
    hdd?: number;
    achat?: Moment;
    stagiaires?: IStagiaire[];
}

export class Ordinateur implements IOrdinateur {
    constructor(
        public id?: number,
        public code?: number,
        public coutParJour?: number,
        public processeur?: string,
        public ram?: number,
        public hdd?: number,
        public achat?: Moment,
        public stagiaires?: IStagiaire[]
    ) {}
}
