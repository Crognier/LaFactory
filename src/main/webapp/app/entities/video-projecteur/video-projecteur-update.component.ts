import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IVideoProjecteur } from 'app/shared/model/video-projecteur.model';
import { VideoProjecteurService } from './video-projecteur.service';

@Component({
    selector: 'jhi-video-projecteur-update',
    templateUrl: './video-projecteur-update.component.html'
})
export class VideoProjecteurUpdateComponent implements OnInit {
    videoProjecteur: IVideoProjecteur;
    isSaving: boolean;

    constructor(private videoProjecteurService: VideoProjecteurService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ videoProjecteur }) => {
            this.videoProjecteur = videoProjecteur;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.videoProjecteur.id !== undefined) {
            this.subscribeToSaveResponse(this.videoProjecteurService.update(this.videoProjecteur));
        } else {
            this.subscribeToSaveResponse(this.videoProjecteurService.create(this.videoProjecteur));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IVideoProjecteur>>) {
        result.subscribe((res: HttpResponse<IVideoProjecteur>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
