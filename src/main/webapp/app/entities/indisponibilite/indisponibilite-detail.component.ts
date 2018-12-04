import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIndisponibilite } from 'app/shared/model/indisponibilite.model';

@Component({
    selector: 'jhi-indisponibilite-detail',
    templateUrl: './indisponibilite-detail.component.html'
})
export class IndisponibiliteDetailComponent implements OnInit {
    indisponibilite: IIndisponibilite;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ indisponibilite }) => {
            this.indisponibilite = indisponibilite;
        });
    }

    previousState() {
        window.history.back();
    }
}
