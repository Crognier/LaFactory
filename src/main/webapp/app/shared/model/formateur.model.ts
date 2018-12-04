import { IFormateurMatiere } from 'app/shared/model//formateur-matiere.model';
import { IModule } from 'app/shared/model//module.model';
import { IIndisponibilite } from 'app/shared/model//indisponibilite.model';

export interface IFormateur {
    id?: number;
    nom?: string;
    prenom?: string;
    numero?: number;
    rue?: string;
    codePostal?: number;
    ville?: string;
    numeroTelephone?: number;
    eMail?: string;
    formateurMatieres?: IFormateurMatiere[];
    modules?: IModule[];
    indisponibilites?: IIndisponibilite[];
}

export class Formateur implements IFormateur {
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
        public formateurMatieres?: IFormateurMatiere[],
        public modules?: IModule[],
        public indisponibilites?: IIndisponibilite[]
    ) {}
}
