import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IFormateur } from 'app/shared/model/formateur.model';
import { FormateurService } from './formateur.service';

@Component({
    selector: 'jhi-formateur-update',
    templateUrl: './formateur-update.component.html'
})
export class FormateurUpdateComponent implements OnInit {
    formateur: IFormateur;
    isSaving: boolean;

    constructor(private formateurService: FormateurService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ formateur }) => {
            this.formateur = formateur;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.formateur.id !== undefined) {
            this.subscribeToSaveResponse(this.formateurService.update(this.formateur));
        } else {
            this.subscribeToSaveResponse(this.formateurService.create(this.formateur));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IFormateur>>) {
        result.subscribe((res: HttpResponse<IFormateur>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
