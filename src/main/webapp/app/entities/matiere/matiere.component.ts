import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IMatiere } from 'app/shared/model/matiere.model';
import { Principal } from 'app/core';
import { MatiereService } from './matiere.service';

@Component({
    selector: 'jhi-matiere',
    templateUrl: './matiere.component.html'
})
export class MatiereComponent implements OnInit, OnDestroy {
    matieres: IMatiere[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private matiereService: MatiereService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.matiereService.query().subscribe(
            (res: HttpResponse<IMatiere[]>) => {
                this.matieres = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInMatieres();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IMatiere) {
        return item.id;
    }

    registerChangeInMatieres() {
        this.eventSubscriber = this.eventManager.subscribe('matiereListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
