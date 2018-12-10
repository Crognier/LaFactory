import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IFormateurMatiere } from 'app/shared/model/formateur-matiere.model';
import { FormateurMatiereService } from './formateur-matiere.service';
import { IFormateur } from 'app/shared/model/formateur.model';
import { FormateurService } from 'app/entities/formateur';
import { IMatiere } from 'app/shared/model/matiere.model';
import { MatiereService } from 'app/entities/matiere';
import { Principal } from 'app/core';

@Component({
    selector: 'jhi-formateur-matiere-update',
    templateUrl: './formateur-matiere-update.component.html'
})
export class FormateurMatiereUpdateComponent implements OnInit {
    formateurMatiere: IFormateurMatiere;
    isSaving: boolean;
    private account: Account;
    private id_account: number;
    private formateur: IFormateur;
    formateurs: IFormateur[];

    matieres: IMatiere[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private formateurMatiereService: FormateurMatiereService,
        private formateurService: FormateurService,
        private matiereService: MatiereService,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {}

    ngOnInit() {
        this.isSaving = false;

        this.activatedRoute.data.subscribe(({ formateurMatiere }) => {
            this.formateurMatiere = formateurMatiere;
        });
        this.formateurService.query().subscribe(
            (res: HttpResponse<IFormateur[]>) => {
                this.formateurs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.matiereService.query().subscribe(
            (res: HttpResponse<IMatiere[]>) => {
                this.matieres = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

        this.principal.identity().then(account => {
            this.account = account;
            if (account.authorities.includes('ROLE_FORMATEUR')) {
                this.id_account = parseInt(this.account.id, 10);
                this.formateurService.findByAccountId(this.id_account).subscribe(
                    (res: HttpResponse<IFormateur>) => {
                        this.formateur = res.body;
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            }
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.formateurMatiere.id !== undefined) {
            this.subscribeToSaveResponse(this.formateurMatiereService.update(this.formateurMatiere));
        } else {
            this.formateurMatiere.formateur = this.formateur;
            this.subscribeToSaveResponse(this.formateurMatiereService.create(this.formateurMatiere));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IFormateurMatiere>>) {
        result.subscribe((res: HttpResponse<IFormateurMatiere>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackMatiereById(index: number, item: IMatiere) {
        return item.id;
    }
}
