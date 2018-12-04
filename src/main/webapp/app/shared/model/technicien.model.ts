export interface ITechnicien {
    id?: number;
    nom?: string;
    prenom?: string;
    numero?: number;
    rue?: string;
    codePostal?: number;
    ville?: string;
    numeroTelephone?: number;
    eMail?: string;
}

export class Technicien implements ITechnicien {
    constructor(
        public id?: number,
        public nom?: string,
        public prenom?: string,
        public numero?: number,
        public rue?: string,
        public codePostal?: number,
        public ville?: string,
        public numeroTelephone?: number,
        public eMail?: string
    ) {}
}
