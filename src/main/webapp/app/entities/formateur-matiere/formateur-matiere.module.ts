import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LaFactorySharedModule } from 'app/shared';
import {
    FormateurMatiereComponent,
    FormateurMatiereDetailComponent,
    FormateurMatiereUpdateComponent,
    FormateurMatiereDeletePopupComponent,
    FormateurMatiereDeleteDialogComponent,
    formateurMatiereRoute,
    formateurMatierePopupRoute
} from './';

const ENTITY_STATES = [...formateurMatiereRoute, ...formateurMatierePopupRoute];

@NgModule({
    imports: [LaFactorySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FormateurMatiereComponent,
        FormateurMatiereDetailComponent,
        FormateurMatiereUpdateComponent,
        FormateurMatiereDeleteDialogComponent,
        FormateurMatiereDeletePopupComponent
    ],
    entryComponents: [
        FormateurMatiereComponent,
        FormateurMatiereUpdateComponent,
        FormateurMatiereDeleteDialogComponent,
        FormateurMatiereDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LaFactoryFormateurMatiereModule {}
