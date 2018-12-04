import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStagiaire } from 'app/shared/model/stagiaire.model';

@Component({
    selector: 'jhi-stagiaire-detail',
    templateUrl: './stagiaire-detail.component.html'
})
export class StagiaireDetailComponent implements OnInit {
    stagiaire: IStagiaire;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ stagiaire }) => {
            this.stagiaire = stagiaire;
        });
    }

    previousState() {
        window.history.back();
    }
}
