import { IOrdinateur } from 'app/shared/model//ordinateur.model';
import { ICursus } from 'app/shared/model//cursus.model';

export const enum Niveau {
    DEBUTANT = 'DEBUTANT',
    INTERMEDIAIRE = 'INTERMEDIAIRE',
    AVANCE = 'AVANCE',
    EXPERT = 'EXPERT'
}

export interface IStagiaire {
    id?: number;
    nom?: string;
    prenom?: string;
    numero?: number;
    rue?: string;
    codePostal?: number;
    ville?: string;
    numeroTelephone?: number;
    eMail?: string;
    niveau?: Niveau;
    ordinateur?: IOrdinateur;
    cursus?: ICursus;
}

export class Stagiaire implements IStagiaire {
    constructor(
        public id?: number,
        public nom?: string,
        public prenom?: string,
        public numero?: number,
        public rue?: string,
        public codePostal?: number,
        public ville?: string,
        public numeroTelephone?: number,
        public eMail?: string,
        public niveau?: Niveau,
        public ordinateur?: IOrdinateur,
        public cursus?: ICursus
    ) {}
}
