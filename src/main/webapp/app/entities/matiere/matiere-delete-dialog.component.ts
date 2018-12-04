import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMatiere } from 'app/shared/model/matiere.model';
import { MatiereService } from './matiere.service';

@Component({
    selector: 'jhi-matiere-delete-dialog',
    templateUrl: './matiere-delete-dialog.component.html'
})
export class MatiereDeleteDialogComponent {
    matiere: IMatiere;

    constructor(private matiereService: MatiereService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.matiereService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'matiereListModification',
                content: 'Deleted an matiere'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-matiere-delete-popup',
    template: ''
})
export class MatiereDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ matiere }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MatiereDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.matiere = matiere;
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
