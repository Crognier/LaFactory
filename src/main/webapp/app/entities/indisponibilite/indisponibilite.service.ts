import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IIndisponibilite } from 'app/shared/model/indisponibilite.model';

type EntityResponseType = HttpResponse<IIndisponibilite>;
type EntityArrayResponseType = HttpResponse<IIndisponibilite[]>;

@Injectable({ providedIn: 'root' })
export class IndisponibiliteService {
    public resourceUrl = SERVER_API_URL + 'api/indisponibilites';

    constructor(private http: HttpClient) {}

    create(indisponibilite: IIndisponibilite): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(indisponibilite);
        return this.http
            .post<IIndisponibilite>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(indisponibilite: IIndisponibilite): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(indisponibilite);
        return this.http
            .put<IIndisponibilite>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IIndisponibilite>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IIndisponibilite[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(indisponibilite: IIndisponibilite): IIndisponibilite {
        const copy: IIndisponibilite = Object.assign({}, indisponibilite, {
            dateDebut:
                indisponibilite.dateDebut != null && indisponibilite.dateDebut.isValid()
                    ? indisponibilite.dateDebut.format(DATE_FORMAT)
                    : null
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
            res.body.forEach((indisponibilite: IIndisponibilite) => {
                indisponibilite.dateDebut = indisponibilite.dateDebut != null ? moment(indisponibilite.dateDebut) : null;
            });
        }
        return res;
    }
}
