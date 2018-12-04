import { Moment } from 'moment';
import { IFormateur } from 'app/shared/model//formateur.model';

export interface IIndisponibilite {
    id?: number;
    dateDebut?: Moment;
    duree?: number;
    formateur?: IFormateur;
}

export class Indisponibilite implements IIndisponibilite {
    constructor(public id?: number, public dateDebut?: Moment, public duree?: number, public formateur?: IFormateur) {}
}
