import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Cursus } from 'app/shared/model/cursus.model';
import { CursusService } from './cursus.service';
import { CursusComponent } from './cursus.component';
import { CursusDetailComponent } from './cursus-detail.component';
import { CursusUpdateComponent } from './cursus-update.component';
import { CursusDeletePopupComponent } from './cursus-delete-dialog.component';
import { ICursus } from 'app/shared/model/cursus.model';

@Injectable({ providedIn: 'root' })
export class CursusResolve implements Resolve<ICursus> {
    constructor(private service: CursusService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Cursus> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Cursus>) => response.ok),
                map((cursus: HttpResponse<Cursus>) => cursus.body)
            );
        }
        return of(new Cursus());
    }
}

export const cursusRoute: Routes = [
    {
        path: 'cursus',
        component: CursusComponent,
        data: {
            authorities: ['ROLE_USER', 'ROLE_GESTIONNAIRE'],
            pageTitle: 'Cursuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cursus/:id/view',
        component: CursusDetailComponent,
        resolve: {
            cursus: CursusResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_GESTIONNAIRE'],
            pageTitle: 'Cursuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cursus/new',
        component: CursusUpdateComponent,
        resolve: {
            cursus: CursusResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_GESTIONNAIRE'],
            pageTitle: 'Cursuses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cursus/:id/edit',
        component: CursusUpdateComponent,
        resolve: {
            cursus: CursusResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_GESTIONNAIRE'],
            pageTitle: 'Cursuses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cursusPopupRoute: Routes = [
    {
        path: 'cursus/:id/delete',
        component: CursusDeletePopupComponent,
        resolve: {
            cursus: CursusResolve
        },
        data: {
            authorities: ['ROLE_USER', 'ROLE_GESTIONNAIRE'],
            pageTitle: 'Cursuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
