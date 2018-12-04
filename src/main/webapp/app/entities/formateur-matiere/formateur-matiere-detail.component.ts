import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFormateurMatiere } from 'app/shared/model/formateur-matiere.model';

@Component({
    selector: 'jhi-formateur-matiere-detail',
    templateUrl: './formateur-matiere-detail.component.html'
})
export class FormateurMatiereDetailComponent implements OnInit {
    formateurMatiere: IFormateurMatiere;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ formateurMatiere }) => {
            this.formateurMatiere = formateurMatiere;
        });
    }

    previousState() {
        window.history.back();
    }
}
