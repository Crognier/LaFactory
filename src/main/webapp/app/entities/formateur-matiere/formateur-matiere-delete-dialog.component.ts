import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFormateurMatiere } from 'app/shared/model/formateur-matiere.model';
import { FormateurMatiereService } from './formateur-matiere.service';

@Component({
    selector: 'jhi-formateur-matiere-delete-dialog',
    templateUrl: './formateur-matiere-delete-dialog.component.html'
})
export class FormateurMatiereDeleteDialogComponent {
    formateurMatiere: IFormateurMatiere;

    constructor(
        private formateurMatiereService: FormateurMatiereService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.formateurMatiereService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'formateurMatiereListModification',
                content: 'Deleted an formateurMatiere'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-formateur-matiere-delete-popup',
    template: ''
})
export class FormateurMatiereDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ formateurMatiere }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(FormateurMatiereDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.formateurMatiere = formateurMatiere;
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
