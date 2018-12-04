import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IIndisponibilite } from 'app/shared/model/indisponibilite.model';
import { Principal } from 'app/core';
import { IndisponibiliteService } from './indisponibilite.service';

@Component({
    selector: 'jhi-indisponibilite',
    templateUrl: './indisponibilite.component.html'
})
export class IndisponibiliteComponent implements OnInit, OnDestroy {
    indisponibilites: IIndisponibilite[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private indisponibiliteService: IndisponibiliteService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.indisponibiliteService.query().subscribe(
            (res: HttpResponse<IIndisponibilite[]>) => {
                this.indisponibilites = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInIndisponibilites();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IIndisponibilite) {
        return item.id;
    }

    registerChangeInIndisponibilites() {
        this.eventSubscriber = this.eventManager.subscribe('indisponibiliteListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
