import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICursus } from 'app/shared/model/cursus.model';

import moment = require('moment');
import { Moment } from 'moment';

@Component({
    selector: 'jhi-cursus-detail',
    templateUrl: './cursus-detail.component.html'
})
export class CursusDetailComponent implements OnInit {
    cursus: ICursus;
    listDate: Array<Moment> = new Array<Moment>();

    constructor(private activatedRoute: ActivatedRoute) {}

    public IncrementerCalendar = function(nbredejour: number, date: Moment) {
        let i: number;
        this.listDate.push(date.format('YYYY-MM-DD'));
        for (i = 0; i < nbredejour - 1; i++) {
            this.listDate.push(date.add(1, 'd').format('YYYY-MM-DD'));
        }
        console.log(this.listDate);
        return this.listDate;
    };

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ cursus }) => {
            this.cursus = cursus;
        });
        this.IncrementerCalendar(this.cursus.duree, moment(this.cursus.dateDebut));
    }

    previousState() {
        window.history.back();
    }
}
