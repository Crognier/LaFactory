import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICursus } from 'app/shared/model/cursus.model';
import { Principal } from 'app/core';
import { CursusService } from './cursus.service';

@Component({
    selector: 'jhi-cursus',
    templateUrl: './cursus.component.html'
})
export class CursusComponent implements OnInit, OnDestroy {
    cursuses: ICursus[];
    currentAccount: any;
    eventSubscriber: Subscription;
    listDate: Array<Date> = new Array<Date>();

    constructor(
        private cursusService: CursusService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}
    loadAll() {
        this.cursusService.query().subscribe(
            (res: HttpResponse<ICursus[]>) => {
                this.cursuses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCursuses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICursus) {
        return item.id;
    }

    registerChangeInCursuses() {
        this.eventSubscriber = this.eventManager.subscribe('cursusListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
