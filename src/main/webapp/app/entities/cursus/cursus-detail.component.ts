import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICursus } from 'app/shared/model/cursus.model';
import { Module } from 'app/shared/model/module.model';
import { Moment } from 'moment';
import moment = require('moment');

@Component({
    selector: 'jhi-cursus-detail',
    templateUrl: './cursus-detail.component.html'
})
export class CursusDetailComponent implements OnInit {
    cursus: ICursus;
    listDate: Array<Moment> = new Array<Moment>();
    dateModuleMap = new Map<Moment, Module>();
    constructor(private activatedRoute: ActivatedRoute) {}

    public IncrementerCalendar = function(nbredejour: number, date: Moment) {
        let i: number;
        this.listDate.push(date.format('YYYY-MM-DD'));
        for (i = 0; i < nbredejour - 1; i++) {
            this.listDate.push(date.add(1, 'd').format('YYYY-MM-DD'));
        }
    };

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ cursus }) => {
            this.cursus = cursus;
        });
        this.cursus.modules.sort((a: Module, b: Module) => {
            return a.dateDebut < b.dateDebut ? -1 : a.dateDebut > b.dateDebut ? 1 : 0;
        });
        this.IncrementerCalendar(this.cursus.duree, moment(this.cursus.dateDebut));
        this.createMapModules(this.cursus.dateDebut);
        console.log(this.dateModuleMap);
    }

    previousState() {
        window.history.back();
    }
    createMapModules(date: Moment) {
        let j = 0;
        for (let i = 0; i < this.cursus.duree; ) {
            for (let k = 0; k < this.cursus.modules[j].duree; ) {
                if (moment(date).isoWeekday() === 6 || moment(date).isoWeekday() === 7) {
                    // @ts-ignore
                    this.dateModuleMap.set(moment(date).format('DD/MM/YYYY'), null);
                    date.add(1, 'd');
                } else {
                    // @ts-ignore
                    this.dateModuleMap.set(moment(date).format('DD/MM/YYYY'), this.cursus.modules[j]);
                    k++;
                    i++;
                    date.add(1, 'd');
                }
            }
            j++;
            console.log(this.dateModuleMap);
        }
    }
}
