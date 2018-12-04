import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IIndisponibilite } from 'app/shared/model/indisponibilite.model';
import { IndisponibiliteService } from './indisponibilite.service';
import { IFormateur } from 'app/shared/model/formateur.model';
import { FormateurService } from 'app/entities/formateur';

@Component({
    selector: 'jhi-indisponibilite-update',
    templateUrl: './indisponibilite-update.component.html'
})
export class IndisponibiliteUpdateComponent implements OnInit {
    indisponibilite: IIndisponibilite;
    isSaving: boolean;

    formateurs: IFormateur[];
    dateDebutDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private indisponibiliteService: IndisponibiliteService,
        private formateurService: FormateurService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ indisponibilite }) => {
            this.indisponibilite = indisponibilite;
        });
        this.formateurService.query().subscribe(
            (res: HttpResponse<IFormateur[]>) => {
                this.formateurs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.indisponibilite.id !== undefined) {
            this.subscribeToSaveResponse(this.indisponibiliteService.update(this.indisponibilite));
        } else {
            this.subscribeToSaveResponse(this.indisponibiliteService.create(this.indisponibilite));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IIndisponibilite>>) {
        result.subscribe((res: HttpResponse<IIndisponibilite>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackFormateurById(index: number, item: IFormateur) {
        return item.id;
    }
}
