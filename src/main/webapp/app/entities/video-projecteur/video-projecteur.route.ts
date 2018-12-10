import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { VideoProjecteur } from 'app/shared/model/video-projecteur.model';
import { VideoProjecteurService } from './video-projecteur.service';
import { VideoProjecteurComponent } from './video-projecteur.component';
import { VideoProjecteurDetailComponent } from './video-projecteur-detail.component';
import { VideoProjecteurUpdateComponent } from './video-projecteur-update.component';
import { VideoProjecteurDeletePopupComponent } from './video-projecteur-delete-dialog.component';
import { IVideoProjecteur } from 'app/shared/model/video-projecteur.model';

@Injectable({ providedIn: 'root' })
export class VideoProjecteurResolve implements Resolve<IVideoProjecteur> {
    constructor(private service: VideoProjecteurService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<VideoProjecteur> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<VideoProjecteur>) => response.ok),
                map((videoProjecteur: HttpResponse<VideoProjecteur>) => videoProjecteur.body)
            );
        }
        return of(new VideoProjecteur());
    }
}

export const videoProjecteurRoute: Routes = [
    {
        path: 'video-projecteur',
        component: VideoProjecteurComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_GESTIONNAIRE', 'ROLE_TECHNICIEN'],
            pageTitle: 'VideoProjecteurs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'video-projecteur/:id/view',
        component: VideoProjecteurDetailComponent,
        resolve: {
            videoProjecteur: VideoProjecteurResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_GESTIONNAIRE', 'ROLE_TECHNICIEN'],
            pageTitle: 'VideoProjecteurs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'video-projecteur/new',
        component: VideoProjecteurUpdateComponent,
        resolve: {
            videoProjecteur: VideoProjecteurResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_GESTIONNAIRE', 'ROLE_TECHNICIEN'],
            pageTitle: 'VideoProjecteurs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'video-projecteur/:id/edit',
        component: VideoProjecteurUpdateComponent,
        resolve: {
            videoProjecteur: VideoProjecteurResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_GESTIONNAIRE', 'ROLE_TECHNICIEN'],
            pageTitle: 'VideoProjecteurs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const videoProjecteurPopupRoute: Routes = [
    {
        path: 'video-projecteur/:id/delete',
        component: VideoProjecteurDeletePopupComponent,
        resolve: {
            videoProjecteur: VideoProjecteurResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_GESTIONNAIRE', 'ROLE_TECHNICIEN'],
            pageTitle: 'VideoProjecteurs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
