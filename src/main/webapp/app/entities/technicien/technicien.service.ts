import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITechnicien } from 'app/shared/model/technicien.model';

type EntityResponseType = HttpResponse<ITechnicien>;
type EntityArrayResponseType = HttpResponse<ITechnicien[]>;

@Injectable({ providedIn: 'root' })
export class TechnicienService {
    public resourceUrl = SERVER_API_URL + 'api/techniciens';

    constructor(private http: HttpClient) {}

    create(technicien: ITechnicien): Observable<EntityResponseType> {
        return this.http.post<ITechnicien>(this.resourceUrl, technicien, { observe: 'response' });
    }

    update(technicien: ITechnicien): Observable<EntityResponseType> {
        return this.http.put<ITechnicien>(this.resourceUrl, technicien, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITechnicien>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITechnicien[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
