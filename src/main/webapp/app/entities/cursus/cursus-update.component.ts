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
import { IModule } from 'app/shared/model/module.model';
import { ModuleService } from 'app/entities/module';
import { IStagiaire } from 'app/shared/model/stagiaire.model';
import { StagiaireService } from 'app/entities/stagiaire';

@Component({
    selector: 'jhi-cursus-update',
    templateUrl: './cursus-update.component.html'
})
export class CursusUpdateComponent implements OnInit {
    cursus: ICursus;
    isSaving: boolean;

    private _gestionnaires: IGestionnaire[];
    private _modules: IModule[] = [];
    private _stagiaires: IStagiaire[] = [];
    dateDebutDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private cursusService: CursusService,
        private gestionnaireService: GestionnaireService,
        private moduleService: ModuleService,
        private stagiaireService: StagiaireService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ cursus }) => {
            this.cursus = cursus;
        });
        this.gestionnaireService.query().subscribe(
            (res: HttpResponse<IGestionnaire[]>) => {
                this._gestionnaires = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.moduleService.query().subscribe(
            (res: HttpResponse<IModule[]>) => {
                this._modules = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.stagiaireService.query().subscribe(
            (res: HttpResponse<IStagiaire[]>) => {
                this._stagiaires = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        console.log(this.cursus);
        this.isSaving = true;
        if (this.cursus.id !== undefined) {
            this.subscribeToSaveResponse(this.cursusService.update(this.cursus));
            this.cursus.modules.forEach(item => {
                console.log(item);
                item.cursus = this.cursus;
                this.subscribeToSaveResponseModule(this.moduleService.update(item));
            });
            this.cursus.stagiaires.forEach(item => {
                console.log(item);
                item.cursus = this.cursus;
                this.subscribeToSaveResponseStagiaire(this.stagiaireService.update(item));
            });
        } else {
            this.subscribeToSaveResponse(this.cursusService.create(this.cursus));
            this.cursus.modules.forEach(item => {
                console.log(item);
                item.cursus = this.cursus;
                this.subscribeToSaveResponseModule(this.moduleService.create(item));
            });
            this.cursus.stagiaires.forEach(item => {
                console.log(item);
                item.cursus = this.cursus;
                this.subscribeToSaveResponseStagiaire(this.stagiaireService.create(item));
            });
        }
    }

    saveModule(module: IModule) {
        this.isSaving = true;
        if (this.cursus.id !== undefined) {
            console.log(module);
            module.cursus = this.cursus;
            this.subscribeToSaveResponseModule(this.moduleService.update(module));
        }
    }

    unsaveModule(module: IModule) {
        this.isSaving = true;
        if (this.cursus.id !== undefined) {
            console.log(module);
            module.cursus = null;
            this.subscribeToSaveResponseModule(this.moduleService.update(module));
        }
    }

    saveStagiaire(stagiaire: IStagiaire) {
        this.isSaving = true;
        if (this.cursus.id !== undefined) {
            console.log(stagiaire);
            stagiaire.cursus = this.cursus;
            this.subscribeToSaveResponseStagiaire(this.stagiaireService.update(stagiaire));
        }
    }

    unsaveStagiaire(stagiaire: IStagiaire) {
        this.isSaving = true;
        if (this.cursus.id !== undefined) {
            console.log(stagiaire);
            stagiaire.cursus = null;
            this.subscribeToSaveResponseStagiaire(this.stagiaireService.update(stagiaire));
        }
    }

    toggleModule(module: IModule) {
        if (module.cursus == null) {
            document.getElementById('unsave-module').setAttribute('disabled', '');
        } else {
            document.getElementById('save-module').setAttribute('disabled', '');
        }
    }

    toggleStagiaire(stagiaire: IStagiaire) {
        if (stagiaire.cursus == null) {
            document.getElementById('unsave-stagiaire').setAttribute('disabled', '');
        } else {
            document.getElementById('save-stagiaire').setAttribute('disabled', '');
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICursus>>) {
        result.subscribe((res: HttpResponse<ICursus>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private subscribeToSaveResponseModule(result: Observable<HttpResponse<IModule>>) {
        result.subscribe((res: HttpResponse<IModule>) => this.onSaveSuccess2(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private subscribeToSaveResponseStagiaire(result: Observable<HttpResponse<IStagiaire>>) {
        result.subscribe((res: HttpResponse<IStagiaire>) => this.onSaveSuccess2(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveSuccess2() {
        this.isSaving = false;
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

    trackModuleById(index: number, item: IModule) {
        return item.id;
    }

    trackStagiaireById(index: number, item: IStagiaire) {
        return item.id;
    }

    get gestionnaires(): IGestionnaire[] {
        return this._gestionnaires;
    }

    set gestionnaires(value: IGestionnaire[]) {
        this._gestionnaires = value;
    }

    get modules(): IModule[] {
        return this._modules;
    }

    set modules(value: IModule[]) {
        this._modules = value;
    }

    get stagiaires(): IStagiaire[] {
        return this._stagiaires;
    }

    set stagiaires(value: IStagiaire[]) {
        this._stagiaires = value;
    }
}
