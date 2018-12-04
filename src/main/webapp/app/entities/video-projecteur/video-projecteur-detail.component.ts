import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVideoProjecteur } from 'app/shared/model/video-projecteur.model';

@Component({
    selector: 'jhi-video-projecteur-detail',
    templateUrl: './video-projecteur-detail.component.html'
})
export class VideoProjecteurDetailComponent implements OnInit {
    videoProjecteur: IVideoProjecteur;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ videoProjecteur }) => {
            this.videoProjecteur = videoProjecteur;
        });
    }

    previousState() {
        window.history.back();
    }
}
