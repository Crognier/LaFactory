import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITechnicien } from 'app/shared/model/technicien.model';
import { Principal } from 'app/core';
import { TechnicienService } from './technicien.service';

@Component({
    selector: 'jhi-technicien',
    templateUrl: './technicien.component.html'
})
export class TechnicienComponent implements OnInit, OnDestroy {
    techniciens: ITechnicien[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private technicienService: TechnicienService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.technicienService.query().subscribe(
            (res: HttpResponse<ITechnicien[]>) => {
                this.techniciens = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTechniciens();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITechnicien) {
        return item.id;
    }

    registerChangeInTechniciens() {
        this.eventSubscriber = this.eventManager.subscribe('technicienListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
