import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IModule } from 'app/shared/model/module.model';
import { ModuleService } from './module.service';
import { ISalle } from 'app/shared/model/salle.model';
import { SalleService } from 'app/entities/salle';
import { IVideoProjecteur } from 'app/shared/model/video-projecteur.model';
import { VideoProjecteurService } from 'app/entities/video-projecteur';
import { ICursus } from 'app/shared/model/cursus.model';
import { CursusService } from 'app/entities/cursus';
import { IFormateur } from 'app/shared/model/formateur.model';
import { FormateurService } from 'app/entities/formateur';
import { IMatiere } from 'app/shared/model/matiere.model';
import { MatiereService } from 'app/entities/matiere';

@Component({
    selector: 'jhi-module-update',
    templateUrl: './module-update.component.html'
})
export class ModuleUpdateComponent implements OnInit {
    module: IModule;
    isSaving: boolean;

    salles: ISalle[];

    videoprojecteurs: IVideoProjecteur[];

    cursuses: ICursus[];

    formateurs: IFormateur[];

    matieres: IMatiere[];
    dateDebutDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private moduleService: ModuleService,
        private salleService: SalleService,
        private videoProjecteurService: VideoProjecteurService,
        private cursusService: CursusService,
        private formateurService: FormateurService,
        private matiereService: MatiereService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ module }) => {
            this.module = module;
        });
        this.salleService.query().subscribe(
            (res: HttpResponse<ISalle[]>) => {
                this.salles = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.videoProjecteurService.query().subscribe(
            (res: HttpResponse<IVideoProjecteur[]>) => {
                this.videoprojecteurs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.cursusService.query().subscribe(
            (res: HttpResponse<ICursus[]>) => {
                this.cursuses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.module.id !== undefined) {
            this.subscribeToSaveResponse(this.moduleService.update(this.module));
        } else {
            this.subscribeToSaveResponse(this.moduleService.create(this.module));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IModule>>) {
        result.subscribe((res: HttpResponse<IModule>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackSalleById(index: number, item: ISalle) {
        return item.id;
    }

    trackVideoProjecteurById(index: number, item: IVideoProjecteur) {
        return item.id;
    }

    trackCursusById(index: number, item: ICursus) {
        return item.id;
    }

    trackFormateurById(index: number, item: IFormateur) {
        return item.id;
    }

    trackMatiereById(index: number, item: IMatiere) {
        return item.id;
    }
}
