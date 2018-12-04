import { Moment } from 'moment';
import { ISalle } from 'app/shared/model//salle.model';
import { IVideoProjecteur } from 'app/shared/model//video-projecteur.model';
import { ICursus } from 'app/shared/model//cursus.model';
import { IFormateur } from 'app/shared/model//formateur.model';
import { IMatiere } from 'app/shared/model//matiere.model';

export interface IModule {
    id?: number;
    dateDebut?: Moment;
    duree?: number;
    salle?: ISalle;
    videoProjecteur?: IVideoProjecteur;
    cursus?: ICursus;
    formateur?: IFormateur;
    matiere?: IMatiere;
}

export class Module implements IModule {
    constructor(
        public id?: number,
        public dateDebut?: Moment,
        public duree?: number,
        public salle?: ISalle,
        public videoProjecteur?: IVideoProjecteur,
        public cursus?: ICursus,
        public formateur?: IFormateur,
        public matiere?: IMatiere
    ) {}
}
