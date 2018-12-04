import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVideoProjecteur } from 'app/shared/model/video-projecteur.model';

type EntityResponseType = HttpResponse<IVideoProjecteur>;
type EntityArrayResponseType = HttpResponse<IVideoProjecteur[]>;

@Injectable({ providedIn: 'root' })
export class VideoProjecteurService {
    public resourceUrl = SERVER_API_URL + 'api/video-projecteurs';

    constructor(private http: HttpClient) {}

    create(videoProjecteur: IVideoProjecteur): Observable<EntityResponseType> {
        return this.http.post<IVideoProjecteur>(this.resourceUrl, videoProjecteur, { observe: 'response' });
    }

    update(videoProjecteur: IVideoProjecteur): Observable<EntityResponseType> {
        return this.http.put<IVideoProjecteur>(this.resourceUrl, videoProjecteur, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IVideoProjecteur>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IVideoProjecteur[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
