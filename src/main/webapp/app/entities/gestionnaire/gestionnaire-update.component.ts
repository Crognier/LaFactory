import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IGestionnaire } from 'app/shared/model/gestionnaire.model';
import { GestionnaireService } from './gestionnaire.service';
import { IUser, UserService } from 'app/core';
import { JhiAlertService } from 'ng-jhipster';

@Component({
    selector: 'jhi-gestionnaire-update',
    templateUrl: './gestionnaire-update.component.html'
})
export class GestionnaireUpdateComponent implements OnInit {
    gestionnaire: IGestionnaire;
    isSaving: boolean;

    users: IUser[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private gestionnaireService: GestionnaireService,
        private activatedRoute: ActivatedRoute,
        private userService: UserService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ gestionnaire }) => {
            this.gestionnaire = gestionnaire;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.gestionnaire.id !== undefined) {
            this.subscribeToSaveResponse(this.gestionnaireService.update(this.gestionnaire));
        } else {
            this.subscribeToSaveResponse(this.gestionnaireService.create(this.gestionnaire));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IGestionnaire>>) {
        result.subscribe((res: HttpResponse<IGestionnaire>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
}
