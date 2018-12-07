import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Matiere } from 'app/shared/model/matiere.model';
import { MatiereService } from './matiere.service';
import { MatiereComponent } from './matiere.component';
import { MatiereDetailComponent } from './matiere-detail.component';
import { MatiereUpdateComponent } from './matiere-update.component';
import { MatiereDeletePopupComponent } from './matiere-delete-dialog.component';
import { IMatiere } from 'app/shared/model/matiere.model';

@Injectable({ providedIn: 'root' })
export class MatiereResolve implements Resolve<IMatiere> {
    constructor(private service: MatiereService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Matiere> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Matiere>) => response.ok),
                map((matiere: HttpResponse<Matiere>) => matiere.body)
            );
        }
        return of(new Matiere());
    }
}

export const matiereRoute: Routes = [
    {
        path: 'matiere',
        component: MatiereComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_FORMATEUR', 'ROLE_GESTIONNAIRE'],
            pageTitle: 'Matieres'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'matiere/:id/view',
        component: MatiereDetailComponent,
        resolve: {
            matiere: MatiereResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_FORMATEUR', 'ROLE_GESTIONNAIRE'],
            pageTitle: 'Matieres'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'matiere/new',
        component: MatiereUpdateComponent,
        resolve: {
            matiere: MatiereResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_FORMATEUR', 'ROLE_GESTIONNAIRE'],
            pageTitle: 'Matieres'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'matiere/:id/edit',
        component: MatiereUpdateComponent,
        resolve: {
            matiere: MatiereResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_FORMATEUR', 'ROLE_GESTIONNAIRE'],
            pageTitle: 'Matieres'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const matierePopupRoute: Routes = [
    {
        path: 'matiere/:id/delete',
        component: MatiereDeletePopupComponent,
        resolve: {
            matiere: MatiereResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_FORMATEUR', 'ROLE_GESTIONNAIRE'],
            pageTitle: 'Matieres'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
