import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FormateurMatiere } from 'app/shared/model/formateur-matiere.model';
import { FormateurMatiereService } from './formateur-matiere.service';
import { FormateurMatiereComponent } from './formateur-matiere.component';
import { FormateurMatiereDetailComponent } from './formateur-matiere-detail.component';
import { FormateurMatiereUpdateComponent } from './formateur-matiere-update.component';
import { FormateurMatiereDeletePopupComponent } from './formateur-matiere-delete-dialog.component';
import { IFormateurMatiere } from 'app/shared/model/formateur-matiere.model';

@Injectable({ providedIn: 'root' })
export class FormateurMatiereResolve implements Resolve<IFormateurMatiere> {
    constructor(private service: FormateurMatiereService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<FormateurMatiere> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<FormateurMatiere>) => response.ok),
                map((formateurMatiere: HttpResponse<FormateurMatiere>) => formateurMatiere.body)
            );
        }
        return of(new FormateurMatiere());
    }
}

export const formateurMatiereRoute: Routes = [
    {
        path: 'formateur-matiere',
        component: FormateurMatiereComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FormateurMatieres'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'formateur-matiere/:id/view',
        component: FormateurMatiereDetailComponent,
        resolve: {
            formateurMatiere: FormateurMatiereResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FormateurMatieres'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'formateur-matiere/new',
        component: FormateurMatiereUpdateComponent,
        resolve: {
            formateurMatiere: FormateurMatiereResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FormateurMatieres'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'formateur-matiere/:id/edit',
        component: FormateurMatiereUpdateComponent,
        resolve: {
            formateurMatiere: FormateurMatiereResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FormateurMatieres'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const formateurMatierePopupRoute: Routes = [
    {
        path: 'formateur-matiere/:id/delete',
        component: FormateurMatiereDeletePopupComponent,
        resolve: {
            formateurMatiere: FormateurMatiereResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FormateurMatieres'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
