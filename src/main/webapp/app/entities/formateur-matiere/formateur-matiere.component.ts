import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFormateurMatiere } from 'app/shared/model/formateur-matiere.model';
import { AccountService, Principal } from 'app/core';
import { FormateurMatiereService } from './formateur-matiere.service';
import { log } from 'util';
import { FormateurService } from 'app/entities/formateur';
import { IFormateur } from 'app/shared/model/formateur.model';

@Component({
    selector: 'jhi-formateur-matiere',
    templateUrl: './formateur-matiere.component.html'
})
export class FormateurMatiereComponent implements OnInit, OnDestroy {
    formateurMatieres: IFormateurMatiere[];
    currentAccount: any;
    eventSubscriber: Subscription;
    private account: Account;
    private formateur: IFormateur;
    private id_account: number;
    private formateurMatiere: IFormateurMatiere;

    constructor(
        private formateurMatiereService: FormateurMatiereService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal,
        private formateurService: FormateurService
    ) {}

    loadAll() {
        this.formateurMatiereService.query().subscribe(
            (res: HttpResponse<IFormateurMatiere[]>) => {
                this.formateurMatieres = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    loadSpecific() {
        this.id_account = parseInt(this.account.id, 10);
        this.formateurService.findByAccountId(this.id_account).subscribe(
            (res: HttpResponse<IFormateur>) => {
                this.formateur = res.body;
                this.formateurMatiereService.findByFormateurId(this.formateur.id).subscribe((res2: HttpResponse<IFormateurMatiere[]>) => {
                    this.formateurMatieres = res2.body;
                });
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.principal.identity().then(account => {
            this.currentAccount = account;
            this.account = account;
            if (account.authorities.includes('ROLE_FORMATEUR')) {
                this.loadSpecific();
            } else {
                this.loadAll();
            }
        });

        this.registerChangeInFormateurMatieres();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    isFormateur() {
        return this.principal.hasAuthority('ROLE_FORMATEUR');
    }

    trackId(index: number, item: IFormateurMatiere) {
        return item.id;
    }

    registerChangeInFormateurMatieres() {
        this.eventSubscriber = this.eventManager.subscribe('formateurMatiereListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
