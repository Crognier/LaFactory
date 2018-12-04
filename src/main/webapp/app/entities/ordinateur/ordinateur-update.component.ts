import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { IOrdinateur } from 'app/shared/model/ordinateur.model';
import { OrdinateurService } from './ordinateur.service';

@Component({
    selector: 'jhi-ordinateur-update',
    templateUrl: './ordinateur-update.component.html'
})
export class OrdinateurUpdateComponent implements OnInit {
    ordinateur: IOrdinateur;
    isSaving: boolean;
    achatDp: any;

    constructor(private ordinateurService: OrdinateurService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ ordinateur }) => {
            this.ordinateur = ordinateur;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.ordinateur.id !== undefined) {
            this.subscribeToSaveResponse(this.ordinateurService.update(this.ordinateur));
        } else {
            this.subscribeToSaveResponse(this.ordinateurService.create(this.ordinateur));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IOrdinateur>>) {
        result.subscribe((res: HttpResponse<IOrdinateur>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
