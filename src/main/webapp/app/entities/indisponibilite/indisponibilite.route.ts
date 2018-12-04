import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Indisponibilite } from 'app/shared/model/indisponibilite.model';
import { IndisponibiliteService } from './indisponibilite.service';
import { IndisponibiliteComponent } from './indisponibilite.component';
import { IndisponibiliteDetailComponent } from './indisponibilite-detail.component';
import { IndisponibiliteUpdateComponent } from './indisponibilite-update.component';
import { IndisponibiliteDeletePopupComponent } from './indisponibilite-delete-dialog.component';
import { IIndisponibilite } from 'app/shared/model/indisponibilite.model';

@Injectable({ providedIn: 'root' })
export class IndisponibiliteResolve implements Resolve<IIndisponibilite> {
    constructor(private service: IndisponibiliteService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Indisponibilite> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Indisponibilite>) => response.ok),
                map((indisponibilite: HttpResponse<Indisponibilite>) => indisponibilite.body)
            );
        }
        return of(new Indisponibilite());
    }
}

export const indisponibiliteRoute: Routes = [
    {
        path: 'indisponibilite',
        component: IndisponibiliteComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Indisponibilites'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'indisponibilite/:id/view',
        component: IndisponibiliteDetailComponent,
        resolve: {
            indisponibilite: IndisponibiliteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Indisponibilites'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'indisponibilite/new',
        component: IndisponibiliteUpdateComponent,
        resolve: {
            indisponibilite: IndisponibiliteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Indisponibilites'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'indisponibilite/:id/edit',
        component: IndisponibiliteUpdateComponent,
        resolve: {
            indisponibilite: IndisponibiliteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Indisponibilites'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const indisponibilitePopupRoute: Routes = [
    {
        path: 'indisponibilite/:id/delete',
        component: IndisponibiliteDeletePopupComponent,
        resolve: {
            indisponibilite: IndisponibiliteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Indisponibilites'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
