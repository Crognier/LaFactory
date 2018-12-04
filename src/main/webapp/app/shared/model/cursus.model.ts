import { Moment } from 'moment';
import { IStagiaire } from 'app/shared/model//stagiaire.model';
import { IModule } from 'app/shared/model//module.model';
import { IGestionnaire } from 'app/shared/model//gestionnaire.model';

export interface ICursus {
    id?: number;
    dateDebut?: Moment;
    duree?: number;
    stagiaires?: IStagiaire[];
    modules?: IModule[];
    gestionnaire?: IGestionnaire;
}

export class Cursus implements ICursus {
    constructor(
        public id?: number,
        public dateDebut?: Moment,
        public duree?: number,
        public stagiaires?: IStagiaire[],
        public modules?: IModule[],
        public gestionnaire?: IGestionnaire
    ) {}
}
