import { IFormateurMatiere } from 'app/shared/model//formateur-matiere.model';
import { IModule } from 'app/shared/model//module.model';

export interface IMatiere {
    id?: number;
    titre?: string;
    objectifs?: string;
    prerequis?: string;
    contenu?: string;
    formateurMatieres?: IFormateurMatiere[];
    modules?: IModule[];
}

export class Matiere implements IMatiere {
    constructor(
        public id?: number,
        public titre?: string,
        public objectifs?: string,
        public prerequis?: string,
        public contenu?: string,
        public formateurMatieres?: IFormateurMatiere[],
        public modules?: IModule[]
    ) {}
}
