import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Technicien } from 'app/shared/model/technicien.model';
import { TechnicienService } from './technicien.service';
import { TechnicienComponent } from './technicien.component';
import { TechnicienDetailComponent } from './technicien-detail.component';
import { TechnicienUpdateComponent } from './technicien-update.component';
import { TechnicienDeletePopupComponent } from './technicien-delete-dialog.component';
import { ITechnicien } from 'app/shared/model/technicien.model';

@Injectable({ providedIn: 'root' })
export class TechnicienResolve implements Resolve<ITechnicien> {
    constructor(private service: TechnicienService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Technicien> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Technicien>) => response.ok),
                map((technicien: HttpResponse<Technicien>) => technicien.body)
            );
        }
        return of(new Technicien());
    }
}

export const technicienRoute: Routes = [
    {
        path: 'technicien',
        component: TechnicienComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_GESTIONNAIRE', 'ROLE_TECHNICIEN'],
            pageTitle: 'Techniciens'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'technicien/:id/view',
        component: TechnicienDetailComponent,
        resolve: {
            technicien: TechnicienResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_GESTIONNAIRE', 'ROLE_TECHNICIEN'],
            pageTitle: 'Techniciens'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'technicien/new',
        component: TechnicienUpdateComponent,
        resolve: {
            technicien: TechnicienResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_GESTIONNAIRE', 'ROLE_TECHNICIEN'],
            pageTitle: 'Techniciens'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'technicien/:id/edit',
        component: TechnicienUpdateComponent,
        resolve: {
            technicien: TechnicienResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_GESTIONNAIRE', 'ROLE_TECHNICIEN'],
            pageTitle: 'Techniciens'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const technicienPopupRoute: Routes = [
    {
        path: 'technicien/:id/delete',
        component: TechnicienDeletePopupComponent,
        resolve: {
            technicien: TechnicienResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_GESTIONNAIRE', 'ROLE_TECHNICIEN'],
            pageTitle: 'Techniciens'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
