import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFormateurMatiere } from 'app/shared/model/formateur-matiere.model';
import { IFormateur } from 'app/shared/model/formateur.model';

type EntityResponseType = HttpResponse<IFormateurMatiere>;
type EntityArrayResponseType = HttpResponse<IFormateurMatiere[]>;

@Injectable({ providedIn: 'root' })
export class FormateurMatiereService {
    public resourceUrl = SERVER_API_URL + 'api/formateur-matieres';

    constructor(private http: HttpClient) {}

    create(formateurMatiere: IFormateurMatiere): Observable<EntityResponseType> {
        return this.http.post<IFormateurMatiere>(this.resourceUrl, formateurMatiere, { observe: 'response' });
    }

    update(formateurMatiere: IFormateurMatiere): Observable<EntityResponseType> {
        return this.http.put<IFormateurMatiere>(this.resourceUrl, formateurMatiere, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IFormateurMatiere>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    findByFormateurId(id: number): Observable<EntityArrayResponseType> {
        const options = createRequestOption(id);
        return this.http.get<IFormateurMatiere[]>(`${this.resourceUrl}/formateur/${id}`, { params: options, observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IFormateurMatiere[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
