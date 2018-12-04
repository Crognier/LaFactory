import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVideoProjecteur } from 'app/shared/model/video-projecteur.model';
import { VideoProjecteurService } from './video-projecteur.service';

@Component({
    selector: 'jhi-video-projecteur-delete-dialog',
    templateUrl: './video-projecteur-delete-dialog.component.html'
})
export class VideoProjecteurDeleteDialogComponent {
    videoProjecteur: IVideoProjecteur;

    constructor(
        private videoProjecteurService: VideoProjecteurService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.videoProjecteurService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'videoProjecteurListModification',
                content: 'Deleted an videoProjecteur'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-video-projecteur-delete-popup',
    template: ''
})
export class VideoProjecteurDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ videoProjecteur }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(VideoProjecteurDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.videoProjecteur = videoProjecteur;
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
