import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Formateur } from 'app/shared/model/formateur.model';
import { FormateurService } from './formateur.service';
import { FormateurComponent } from './formateur.component';
import { FormateurDetailComponent } from './formateur-detail.component';
import { FormateurUpdateComponent } from './formateur-update.component';
import { FormateurDeletePopupComponent } from './formateur-delete-dialog.component';
import { IFormateur } from 'app/shared/model/formateur.model';

@Injectable({ providedIn: 'root' })
export class FormateurResolve implements Resolve<IFormateur> {
    constructor(private service: FormateurService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Formateur> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Formateur>) => response.ok),
                map((formateur: HttpResponse<Formateur>) => formateur.body)
            );
        }
        return of(new Formateur());
    }
}

export const formateurRoute: Routes = [
    {
        path: 'formateur',
        component: FormateurComponent,
        data: {
            authorities: ['ROLE_GESTIONNAIRE', 'ROLE_USER'],
            pageTitle: 'Formateurs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'formateur/:id/view',
        component: FormateurDetailComponent,
        resolve: {
            formateur: FormateurResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_GESTIONNAIRE'],
            pageTitle: 'Formateurs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'formateur/new',
        component: FormateurUpdateComponent,
        resolve: {
            formateur: FormateurResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_GESTIONNAIRE'],
            pageTitle: 'Formateurs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'formateur/:id/edit',
        component: FormateurUpdateComponent,
        resolve: {
            formateur: FormateurResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_GESTIONNAIRE'],
            pageTitle: 'Formateurs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const formateurPopupRoute: Routes = [
    {
        path: 'formateur/:id/delete',
        component: FormateurDeletePopupComponent,
        resolve: {
            formateur: FormateurResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_GESTIONNAIRE'],
            pageTitle: 'Formateurs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
