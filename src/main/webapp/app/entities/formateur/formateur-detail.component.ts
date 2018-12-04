import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFormateur } from 'app/shared/model/formateur.model';

@Component({
    selector: 'jhi-formateur-detail',
    templateUrl: './formateur-detail.component.html'
})
export class FormateurDetailComponent implements OnInit {
    formateur: IFormateur;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ formateur }) => {
            this.formateur = formateur;
        });
    }

    previousState() {
        window.history.back();
    }
}
