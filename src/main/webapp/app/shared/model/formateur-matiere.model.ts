import { IFormateur } from 'app/shared/model//formateur.model';
import { IMatiere } from 'app/shared/model//matiere.model';

export const enum Niveau {
    DEBUTANT = 'DEBUTANT',
    INTERMEDIAIRE = 'INTERMEDIAIRE',
    AVANCE = 'AVANCE',
    EXPERT = 'EXPERT'
}

export interface IFormateurMatiere {
    id?: number;
    niveau?: Niveau;
    formateur?: IFormateur;
    matiere?: IMatiere;
}

export class FormateurMatiere implements IFormateurMatiere {
    constructor(public id?: number, public niveau?: Niveau, public formateur?: IFormateur, public matiere?: IMatiere) {}
}
