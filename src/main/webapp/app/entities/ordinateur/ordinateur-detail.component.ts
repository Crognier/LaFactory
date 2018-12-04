import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrdinateur } from 'app/shared/model/ordinateur.model';

@Component({
    selector: 'jhi-ordinateur-detail',
    templateUrl: './ordinateur-detail.component.html'
})
export class OrdinateurDetailComponent implements OnInit {
    ordinateur: IOrdinateur;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ ordinateur }) => {
            this.ordinateur = ordinateur;
        });
    }

    previousState() {
        window.history.back();
    }
}
