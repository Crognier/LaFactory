import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Cursus, ICursus } from 'app/shared/model/cursus.model';
import moment = require('moment');
import { Moment } from 'moment';

@Component({
    selector: 'jhi-cursus-detail',
    templateUrl: './cursus-detail.component.html'
})

export class CursusDetailComponent implements OnInit {
    cursus: ICursus;
    dureeCalendar = 0;
    dateDeFin: String;

    constructor(private activatedRoute: ActivatedRoute) {
    }

    ngOnInit() {

        this.activatedRoute.data.subscribe(({ cursus }) => {
            this.cursus = cursus;
        });
        this.calculerDureeCalendar(moment(this.cursus.dateDebut));
        this.dateDeFin = moment(this.cursus.dateDebut).add(this.dureeCalendar - 1, 'd').format('YYYY-MM-DD');
    }

    previousState() {
        window.history.back();
    }

    calculerDureeCalendar(date: Moment) {
        for (let i = 0; i < this.cursus.duree;) {
            this.dureeCalendar++;
            if ((moment(date).isoWeekday() !== 6) && (moment(date).isoWeekday() !== 7)) {
                i++;
            }
            date.add(1, 'd');
        }
    }
}
