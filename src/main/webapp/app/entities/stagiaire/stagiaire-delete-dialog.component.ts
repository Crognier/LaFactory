import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStagiaire } from 'app/shared/model/stagiaire.model';
import { StagiaireService } from './stagiaire.service';

@Component({
    selector: 'jhi-stagiaire-delete-dialog',
    templateUrl: './stagiaire-delete-dialog.component.html'
})
export class StagiaireDeleteDialogComponent {
    stagiaire: IStagiaire;

    constructor(private stagiaireService: StagiaireService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.stagiaireService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'stagiaireListModification',
                content: 'Deleted an stagiaire'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-stagiaire-delete-popup',
    template: ''
})
export class StagiaireDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ stagiaire }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(StagiaireDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.stagiaire = stagiaire;
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
