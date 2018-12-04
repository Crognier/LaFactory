import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Stagiaire } from 'app/shared/model/stagiaire.model';
import { StagiaireService } from './stagiaire.service';
import { StagiaireComponent } from './stagiaire.component';
import { StagiaireDetailComponent } from './stagiaire-detail.component';
import { StagiaireUpdateComponent } from './stagiaire-update.component';
import { StagiaireDeletePopupComponent } from './stagiaire-delete-dialog.component';
import { IStagiaire } from 'app/shared/model/stagiaire.model';

@Injectable({ providedIn: 'root' })
export class StagiaireResolve implements Resolve<IStagiaire> {
    constructor(private service: StagiaireService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Stagiaire> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Stagiaire>) => response.ok),
                map((stagiaire: HttpResponse<Stagiaire>) => stagiaire.body)
            );
        }
        return of(new Stagiaire());
    }
}

export const stagiaireRoute: Routes = [
    {
        path: 'stagiaire',
        component: StagiaireComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Stagiaires'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'stagiaire/:id/view',
        component: StagiaireDetailComponent,
        resolve: {
            stagiaire: StagiaireResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Stagiaires'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'stagiaire/new',
        component: StagiaireUpdateComponent,
        resolve: {
            stagiaire: StagiaireResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Stagiaires'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'stagiaire/:id/edit',
        component: StagiaireUpdateComponent,
        resolve: {
            stagiaire: StagiaireResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Stagiaires'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const stagiairePopupRoute: Routes = [
    {
        path: 'stagiaire/:id/delete',
        component: StagiaireDeletePopupComponent,
        resolve: {
            stagiaire: StagiaireResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Stagiaires'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
