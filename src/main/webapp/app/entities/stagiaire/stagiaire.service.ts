import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStagiaire } from 'app/shared/model/stagiaire.model';

type EntityResponseType = HttpResponse<IStagiaire>;
type EntityArrayResponseType = HttpResponse<IStagiaire[]>;

@Injectable({ providedIn: 'root' })
export class StagiaireService {
    public resourceUrl = SERVER_API_URL + 'api/stagiaires';

    constructor(private http: HttpClient) {}

    create(stagiaire: IStagiaire): Observable<EntityResponseType> {
        return this.http.post<IStagiaire>(this.resourceUrl, stagiaire, { observe: 'response' });
    }

    update(stagiaire: IStagiaire): Observable<EntityResponseType> {
        return this.http.put<IStagiaire>(this.resourceUrl, stagiaire, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IStagiaire>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IStagiaire[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
