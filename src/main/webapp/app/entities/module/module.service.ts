import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IModule } from 'app/shared/model/module.model';

type EntityResponseType = HttpResponse<IModule>;
type EntityArrayResponseType = HttpResponse<IModule[]>;

@Injectable({ providedIn: 'root' })
export class ModuleService {
    public resourceUrl = SERVER_API_URL + 'api/modules';

    constructor(private http: HttpClient) {}

    create(module: IModule): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(module);
        return this.http
            .post<IModule>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(module: IModule): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(module);
        return this.http
            .put<IModule>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IModule>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IModule[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(module: IModule): IModule {
        const copy: IModule = Object.assign({}, module, {
            dateDebut: module.dateDebut != null && module.dateDebut.isValid() ? module.dateDebut.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateDebut = res.body.dateDebut != null ? moment(res.body.dateDebut) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((module: IModule) => {
                module.dateDebut = module.dateDebut != null ? moment(module.dateDebut) : null;
            });
        }
        return res;
    }
}
