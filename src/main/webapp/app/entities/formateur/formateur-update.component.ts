import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IFormateur } from 'app/shared/model/formateur.model';
import { FormateurService } from './formateur.service';
import { IUser, UserService } from 'app/core';
import { JhiAlertService } from 'ng-jhipster';

@Component({
    selector: 'jhi-formateur-update',
    templateUrl: './formateur-update.component.html'
})
export class FormateurUpdateComponent implements OnInit {
    formateur: IFormateur;
    isSaving: boolean;

    users: IUser[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private formateurService: FormateurService,
        private activatedRoute: ActivatedRoute,
        private userService: UserService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ formateur }) => {
            this.formateur = formateur;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.formateur.id !== undefined) {
            console.log(this.formateur.user)
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

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
}
