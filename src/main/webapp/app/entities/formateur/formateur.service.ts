import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFormateur } from 'app/shared/model/formateur.model';

type EntityResponseType = HttpResponse<IFormateur>;
type EntityArrayResponseType = HttpResponse<IFormateur[]>;

@Injectable({ providedIn: 'root' })
export class FormateurService {
    public resourceUrl = SERVER_API_URL + 'api/formateurs';

    constructor(private http: HttpClient) {}

    create(formateur: IFormateur): Observable<EntityResponseType> {
        return this.http.post<IFormateur>(this.resourceUrl, formateur, { observe: 'response' });
    }

    update(formateur: IFormateur): Observable<EntityResponseType> {
        return this.http.put<IFormateur>(this.resourceUrl, formateur, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IFormateur>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IFormateur[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
