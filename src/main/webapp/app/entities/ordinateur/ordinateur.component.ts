import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IOrdinateur } from 'app/shared/model/ordinateur.model';
import { Principal } from 'app/core';
import { OrdinateurService } from './ordinateur.service';

@Component({
    selector: 'jhi-ordinateur',
    templateUrl: './ordinateur.component.html'
})
export class OrdinateurComponent implements OnInit, OnDestroy {
    ordinateurs: IOrdinateur[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private ordinateurService: OrdinateurService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.ordinateurService.query().subscribe(
            (res: HttpResponse<IOrdinateur[]>) => {
                this.ordinateurs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInOrdinateurs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IOrdinateur) {
        return item.id;
    }

    registerChangeInOrdinateurs() {
        this.eventSubscriber = this.eventManager.subscribe('ordinateurListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
