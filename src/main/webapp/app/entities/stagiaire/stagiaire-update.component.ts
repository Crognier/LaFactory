import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IStagiaire } from 'app/shared/model/stagiaire.model';
import { StagiaireService } from './stagiaire.service';
import { IOrdinateur } from 'app/shared/model/ordinateur.model';
import { OrdinateurService } from 'app/entities/ordinateur';
import { ICursus } from 'app/shared/model/cursus.model';
import { CursusService } from 'app/entities/cursus';

@Component({
    selector: 'jhi-stagiaire-update',
    templateUrl: './stagiaire-update.component.html'
})
export class StagiaireUpdateComponent implements OnInit {
    stagiaire: IStagiaire;
    isSaving: boolean;

    ordinateurs: IOrdinateur[];

    cursuses: ICursus[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private stagiaireService: StagiaireService,
        private ordinateurService: OrdinateurService,
        private cursusService: CursusService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ stagiaire }) => {
            this.stagiaire = stagiaire;
        });
        this.ordinateurService.query().subscribe(
            (res: HttpResponse<IOrdinateur[]>) => {
                this.ordinateurs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.cursusService.query().subscribe(
            (res: HttpResponse<ICursus[]>) => {
                this.cursuses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.stagiaire.id !== undefined) {
            this.subscribeToSaveResponse(this.stagiaireService.update(this.stagiaire));
        } else {
            this.subscribeToSaveResponse(this.stagiaireService.create(this.stagiaire));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IStagiaire>>) {
        result.subscribe((res: HttpResponse<IStagiaire>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackOrdinateurById(index: number, item: IOrdinateur) {
        return item.id;
    }

    trackCursusById(index: number, item: ICursus) {
        return item.id;
    }
}
