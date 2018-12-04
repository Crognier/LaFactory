import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOrdinateur } from 'app/shared/model/ordinateur.model';

type EntityResponseType = HttpResponse<IOrdinateur>;
type EntityArrayResponseType = HttpResponse<IOrdinateur[]>;

@Injectable({ providedIn: 'root' })
export class OrdinateurService {
    public resourceUrl = SERVER_API_URL + 'api/ordinateurs';

    constructor(private http: HttpClient) {}

    create(ordinateur: IOrdinateur): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(ordinateur);
        return this.http
            .post<IOrdinateur>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(ordinateur: IOrdinateur): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(ordinateur);
        return this.http
            .put<IOrdinateur>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IOrdinateur>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IOrdinateur[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(ordinateur: IOrdinateur): IOrdinateur {
        const copy: IOrdinateur = Object.assign({}, ordinateur, {
            achat: ordinateur.achat != null && ordinateur.achat.isValid() ? ordinateur.achat.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.achat = res.body.achat != null ? moment(res.body.achat) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((ordinateur: IOrdinateur) => {
                ordinateur.achat = ordinateur.achat != null ? moment(ordinateur.achat) : null;
            });
        }
        return res;
    }
}
