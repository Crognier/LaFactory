import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IMatiere } from 'app/shared/model/matiere.model';
import { MatiereService } from './matiere.service';

@Component({
    selector: 'jhi-matiere-update',
    templateUrl: './matiere-update.component.html'
})
export class MatiereUpdateComponent implements OnInit {
    matiere: IMatiere;
    isSaving: boolean;

    constructor(private matiereService: MatiereService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ matiere }) => {
            this.matiere = matiere;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.matiere.id !== undefined) {
            this.subscribeToSaveResponse(this.matiereService.update(this.matiere));
        } else {
            this.subscribeToSaveResponse(this.matiereService.create(this.matiere));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IMatiere>>) {
        result.subscribe((res: HttpResponse<IMatiere>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
