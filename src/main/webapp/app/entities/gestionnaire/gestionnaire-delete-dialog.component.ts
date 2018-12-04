import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGestionnaire } from 'app/shared/model/gestionnaire.model';
import { GestionnaireService } from './gestionnaire.service';

@Component({
    selector: 'jhi-gestionnaire-delete-dialog',
    templateUrl: './gestionnaire-delete-dialog.component.html'
})
export class GestionnaireDeleteDialogComponent {
    gestionnaire: IGestionnaire;

    constructor(
        private gestionnaireService: GestionnaireService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.gestionnaireService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'gestionnaireListModification',
                content: 'Deleted an gestionnaire'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-gestionnaire-delete-popup',
    template: ''
})
export class GestionnaireDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ gestionnaire }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(GestionnaireDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.gestionnaire = gestionnaire;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
