import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ITechnicien } from 'app/shared/model/technicien.model';
import { TechnicienService } from './technicien.service';

@Component({
    selector: 'jhi-technicien-update',
    templateUrl: './technicien-update.component.html'
})
export class TechnicienUpdateComponent implements OnInit {
    technicien: ITechnicien;
    isSaving: boolean;

    constructor(private technicienService: TechnicienService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ technicien }) => {
            this.technicien = technicien;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.technicien.id !== undefined) {
            this.subscribeToSaveResponse(this.technicienService.update(this.technicien));
        } else {
            this.subscribeToSaveResponse(this.technicienService.create(this.technicien));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITechnicien>>) {
        result.subscribe((res: HttpResponse<ITechnicien>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
