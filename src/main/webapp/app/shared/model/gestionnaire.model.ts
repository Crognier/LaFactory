import { ICursus } from 'app/shared/model//cursus.model';
import { IUser } from 'app/core';

export interface IGestionnaire {
    id?: number;
    nom?: string;
    prenom?: string;
    numero?: number;
    rue?: string;
    codePostal?: number;
    ville?: string;
    numeroTelephone?: number;
    eMail?: string;
    cursuses?: ICursus[];
    user?: IUser;
}

export class Gestionnaire implements IGestionnaire {
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
        public cursuses?: ICursus[],
        public user?: IUser
    ) {}
}
