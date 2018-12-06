import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { ICursus } from 'app/shared/model/cursus.model';
import { CursusService } from './cursus.service';
import { IGestionnaire } from 'app/shared/model/gestionnaire.model';
import { GestionnaireService } from 'app/entities/gestionnaire';

@Component({
    selector: 'jhi-cursus-update',
    templateUrl: './cursus-update.component.html'
})
export class CursusUpdateComponent implements OnInit {
    cursus: ICursus;
    isSaving: boolean;

    gestionnaires: IGestionnaire[];
    dateDebutDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private cursusService: CursusService,
        private gestionnaireService: GestionnaireService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ cursus }) => {
            this.cursus = cursus;
        });
        this.gestionnaireService.query().subscribe(
            (res: HttpResponse<IGestionnaire[]>) => {
                this.gestionnaires = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;

        if (this.cursus.id !== undefined) {
            this.subscribeToSaveResponse(this.cursusService.update(this.cursus));
            } else {
            this.subscribeToSaveResponse(this.cursusService.create(this.cursus));
            }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICursus>>) {
        result.subscribe((res: HttpResponse<ICursus>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackGestionnaireById(index: number, item: IGestionnaire) {
        return item.id;
    }
}
