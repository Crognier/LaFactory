import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Gestionnaire } from 'app/shared/model/gestionnaire.model';
import { GestionnaireService } from './gestionnaire.service';
import { GestionnaireComponent } from './gestionnaire.component';
import { GestionnaireDetailComponent } from './gestionnaire-detail.component';
import { GestionnaireUpdateComponent } from './gestionnaire-update.component';
import { GestionnaireDeletePopupComponent } from './gestionnaire-delete-dialog.component';
import { IGestionnaire } from 'app/shared/model/gestionnaire.model';

@Injectable({ providedIn: 'root' })
export class GestionnaireResolve implements Resolve<IGestionnaire> {
    constructor(private service: GestionnaireService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Gestionnaire> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Gestionnaire>) => response.ok),
                map((gestionnaire: HttpResponse<Gestionnaire>) => gestionnaire.body)
            );
        }
        return of(new Gestionnaire());
    }
}

export const gestionnaireRoute: Routes = [
    {
        path: 'gestionnaire',
        component: GestionnaireComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Gestionnaires'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'gestionnaire/:id/view',
        component: GestionnaireDetailComponent,
        resolve: {
            gestionnaire: GestionnaireResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Gestionnaires'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'gestionnaire/new',
        component: GestionnaireUpdateComponent,
        resolve: {
            gestionnaire: GestionnaireResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Gestionnaires'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'gestionnaire/:id/edit',
        component: GestionnaireUpdateComponent,
        resolve: {
            gestionnaire: GestionnaireResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Gestionnaires'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const gestionnairePopupRoute: Routes = [
    {
        path: 'gestionnaire/:id/delete',
        component: GestionnaireDeletePopupComponent,
        resolve: {
            gestionnaire: GestionnaireResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Gestionnaires'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
