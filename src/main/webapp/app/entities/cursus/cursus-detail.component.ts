import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICursus } from 'app/shared/model/cursus.model';
import { IModule, Module } from 'app/shared/model/module.model';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { IUser } from 'app/core';
import { ModuleService } from 'app/entities/module';
import { JhiAlertService } from 'ng-jhipster';
import moment = require('moment');

@Component({
    selector: 'jhi-cursus-detail',
    templateUrl: './cursus-detail.component.html'
})
export class CursusDetailComponent implements OnInit {
    cursus: ICursus;
    dateModuleMap = new Map<Date, Module>();

    constructor(
        private activatedRoute: ActivatedRoute) {
    }

    ngOnInit() {

        this.activatedRoute.data.subscribe(({ cursus }) => {
            this.cursus = cursus;
        });
        this.cursus.modules.sort((a: Module, b: Module) => {
            /*   console.log(a);
               console.log(a.dateDebut);
               console.log(a.dateDebut > b.dateDebut);
               console.log(moment(a.dateDebut).toDate());
               console.log(moment(a.dateDebut).isoWeekday());
               console.log(moment(a.dateDebut).toDate().setDate(moment(a.dateDebut).toDate().getDate() + 1));
               console.log(moment(a.dateDebut).toDate().setDate(moment(a.dateDebut).toDate().getDate()));
               console.log(moment(a.dateDebut).add(1, 'd'));
               console.log(moment(a.dateDebut).add(1, 'd').format("YYYY-MM-DD"));
               console.log(a.dateDebut.add(1, 'd'));
               console.log('après ça bugge');
               console.log(b);
               console.log(b.dateDebut); */
            return a.dateDebut < b.dateDebut ? -1 : a.dateDebut > b.dateDebut ? 1 : 0;
        });
        this.createMapModules();
    }

    previousState() {
        window.history.back();
    }

    createMapModules() {
        let j = 0;
        for (let i = 0; i < this.dureeCalendar; i++) {
            console.log(this.listDate[i])
            if (this.listDate[i] === this.cursus.modules[j]) {
                for ( let k = 0; k < this.cursus.modules[j].duree; k++) {
                    if (this.listDate[i].isoWeekday() in [6, 7]) {
                        this.dateModuleMap.set(this.listDate[i], null);
                    } else {
                        this.dateModuleMap.set(this.listDate[i], this.cursus.modules[j]);
                    }
                }
                j++;
            }
        }
    }
}
