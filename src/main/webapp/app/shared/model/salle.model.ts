import { IModule } from 'app/shared/model//module.model';

export interface ISalle {
    id?: number;
    code?: number;
    coutParJour?: number;
    capacite?: number;
    modules?: IModule[];
}

export class Salle implements ISalle {
    constructor(
        public id?: number,
        public code?: number,
        public coutParJour?: number,
        public capacite?: number,
        public modules?: IModule[]
    ) {}
}
