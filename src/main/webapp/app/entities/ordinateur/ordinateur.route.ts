import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Ordinateur } from 'app/shared/model/ordinateur.model';
import { OrdinateurService } from './ordinateur.service';
import { OrdinateurComponent } from './ordinateur.component';
import { OrdinateurDetailComponent } from './ordinateur-detail.component';
import { OrdinateurUpdateComponent } from './ordinateur-update.component';
import { OrdinateurDeletePopupComponent } from './ordinateur-delete-dialog.component';
import { IOrdinateur } from 'app/shared/model/ordinateur.model';

@Injectable({ providedIn: 'root' })
export class OrdinateurResolve implements Resolve<IOrdinateur> {
    constructor(private service: OrdinateurService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Ordinateur> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Ordinateur>) => response.ok),
                map((ordinateur: HttpResponse<Ordinateur>) => ordinateur.body)
            );
        }
        return of(new Ordinateur());
    }
}

export const ordinateurRoute: Routes = [
    {
        path: 'ordinateur',
        component: OrdinateurComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ordinateurs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ordinateur/:id/view',
        component: OrdinateurDetailComponent,
        resolve: {
            ordinateur: OrdinateurResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ordinateurs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ordinateur/new',
        component: OrdinateurUpdateComponent,
        resolve: {
            ordinateur: OrdinateurResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ordinateurs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ordinateur/:id/edit',
        component: OrdinateurUpdateComponent,
        resolve: {
            ordinateur: OrdinateurResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ordinateurs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ordinateurPopupRoute: Routes = [
    {
        path: 'ordinateur/:id/delete',
        component: OrdinateurDeletePopupComponent,
        resolve: {
            ordinateur: OrdinateurResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Ordinateurs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
