import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ISalle } from 'app/shared/model/salle.model';
import { SalleService } from './salle.service';

@Component({
    selector: 'jhi-salle-update',
    templateUrl: './salle-update.component.html'
})
export class SalleUpdateComponent implements OnInit {
    salle: ISalle;
    isSaving: boolean;

    constructor(private salleService: SalleService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ salle }) => {
            this.salle = salle;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.salle.id !== undefined) {
            this.subscribeToSaveResponse(this.salleService.update(this.salle));
        } else {
            this.subscribeToSaveResponse(this.salleService.create(this.salle));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISalle>>) {
        result.subscribe((res: HttpResponse<ISalle>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
