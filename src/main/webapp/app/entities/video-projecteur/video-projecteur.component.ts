import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IVideoProjecteur } from 'app/shared/model/video-projecteur.model';
import { Principal } from 'app/core';
import { VideoProjecteurService } from './video-projecteur.service';

@Component({
    selector: 'jhi-video-projecteur',
    templateUrl: './video-projecteur.component.html'
})
export class VideoProjecteurComponent implements OnInit, OnDestroy {
    videoProjecteurs: IVideoProjecteur[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private videoProjecteurService: VideoProjecteurService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.videoProjecteurService.query().subscribe(
            (res: HttpResponse<IVideoProjecteur[]>) => {
                this.videoProjecteurs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInVideoProjecteurs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IVideoProjecteur) {
        return item.id;
    }

    registerChangeInVideoProjecteurs() {
        this.eventSubscriber = this.eventManager.subscribe('videoProjecteurListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
