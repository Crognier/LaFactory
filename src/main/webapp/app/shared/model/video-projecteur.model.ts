import { IModule } from 'app/shared/model//module.model';

export interface IVideoProjecteur {
    id?: number;
    code?: number;
    coutParJour?: number;
    modules?: IModule[];
}

export class VideoProjecteur implements IVideoProjecteur {
    constructor(public id?: number, public code?: number, public coutParJour?: number, public modules?: IModule[]) {}
}
