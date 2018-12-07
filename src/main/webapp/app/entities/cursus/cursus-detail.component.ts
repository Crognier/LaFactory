import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Cursus, ICursus } from 'app/shared/model/cursus.model';
import moment = require('moment');

@Component({
    selector: 'jhi-cursus-detail',
    templateUrl: './cursus-detail.component.html'
})

export class CursusDetailComponent implements OnInit {
    cursus: ICursus;
    dureeCalendar: number;
    dateDeFin: Date;

    constructor(private activatedRoute: ActivatedRoute) {
    }

    ngOnInit() {

        this.activatedRoute.data.subscribe(({ cursus }) => {
            this.cursus = cursus;
        });
    }

    previousState() {
        window.history.back();
    }

    calculerDureeCalendar(cursus: Cursus) {
        let duree: number;
        let rest: number;
        duree = cursus.duree;
        this.dureeCalendar = (Math.trunc(duree / 5) * 2) + duree;
        this.dateDeFin = moment(cursus.dateDebut).add(((this.dureeCalendar) - 1), 'd').format('YYYY-MM-DD-dd');
        rest = duree % 5;

        if (rest === 0) {
            if ((moment(this.dateDeFin).isoWeekday()) !== 6 || 7) {
                this.dateDeFin = moment(cursus.dateDebut).add(((this.dureeCalendar) - 1), 'd').format('YYYY-MM-DD-dd');
            } else if ((moment(this.dateDeFin).isoWeekday()) === 6) {
                this.dateDeFin = moment(cursus.dateDebut).add(((this.dureeCalendar) - 1 + 2), 'd').format('YYYY-MM-DD-dd');
            } else if ((moment(this.dateDeFin).isoWeekday()) === 7) {
                this.dateDeFin = moment(cursus.dateDebut).add(((this.dureeCalendar) - 1 + 2), 'd').format('YYYY-MM-DD-dd');
            }
        } else if (rest !== 0) {
            if ((moment(this.dateDeFin).isoWeekday()) !== 6 || 7) {
                this.dateDeFin = moment(cursus.dateDebut).add(((this.dureeCalendar)), 'd').format('YYYY-MM-DD-dd');
            } else if ((moment(this.dateDeFin).isoWeekday()) === 6) {
                this.dateDeFin = moment(cursus.dateDebut).add(((this.dureeCalendar) + 2), 'd').format('YYYY-MM-DD-dd');
            } else if ((moment(this.dateDeFin).isoWeekday()) === 7) {
                this.dateDeFin = moment(cursus.dateDebut).add(((this.dureeCalendar) + 2), 'd').format('YYYY-MM-DD-dd');
            }
        }
        console.log('cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc');
        console.log(moment(cursus.dateDebut).add(this.dureeCalendar, 'd').format('YYYY-MM-DD-dd'));
        console.log('cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc');
        console.log(moment(cursus.dateDebut).isoWeekday());

        return this.dureeCalendar;
    }
}
