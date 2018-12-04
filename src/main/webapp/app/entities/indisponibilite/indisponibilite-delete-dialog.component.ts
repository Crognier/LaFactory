import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIndisponibilite } from 'app/shared/model/indisponibilite.model';
import { IndisponibiliteService } from './indisponibilite.service';

@Component({
    selector: 'jhi-indisponibilite-delete-dialog',
    templateUrl: './indisponibilite-delete-dialog.component.html'
})
export class IndisponibiliteDeleteDialogComponent {
    indisponibilite: IIndisponibilite;

    constructor(
        private indisponibiliteService: IndisponibiliteService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.indisponibiliteService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'indisponibiliteListModification',
                content: 'Deleted an indisponibilite'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-indisponibilite-delete-popup',
    template: ''
})
export class IndisponibiliteDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ indisponibilite }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(IndisponibiliteDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.indisponibilite = indisponibilite;
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
