import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICursus } from 'app/shared/model/cursus.model';

@Component({
    selector: 'jhi-cursus-detail',
    templateUrl: './cursus-detail.component.html'
})
export class CursusDetailComponent implements OnInit {
    cursus: ICursus;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ cursus }) => {
            this.cursus = cursus;
        });
    }

    previousState() {
        window.history.back();
    }
}
