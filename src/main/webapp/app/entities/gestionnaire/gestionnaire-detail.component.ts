import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGestionnaire } from 'app/shared/model/gestionnaire.model';

@Component({
    selector: 'jhi-gestionnaire-detail',
    templateUrl: './gestionnaire-detail.component.html'
})
export class GestionnaireDetailComponent implements OnInit {
    gestionnaire: IGestionnaire;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ gestionnaire }) => {
            this.gestionnaire = gestionnaire;
        });
    }

    previousState() {
        window.history.back();
    }
}
