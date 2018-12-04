import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LaFactorySharedModule } from 'app/shared';
import {
    IndisponibiliteComponent,
    IndisponibiliteDetailComponent,
    IndisponibiliteUpdateComponent,
    IndisponibiliteDeletePopupComponent,
    IndisponibiliteDeleteDialogComponent,
    indisponibiliteRoute,
    indisponibilitePopupRoute
} from './';

const ENTITY_STATES = [...indisponibiliteRoute, ...indisponibilitePopupRoute];

@NgModule({
    imports: [LaFactorySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        IndisponibiliteComponent,
        IndisponibiliteDetailComponent,
        IndisponibiliteUpdateComponent,
        IndisponibiliteDeleteDialogComponent,
        IndisponibiliteDeletePopupComponent
    ],
    entryComponents: [
        IndisponibiliteComponent,
        IndisponibiliteUpdateComponent,
        IndisponibiliteDeleteDialogComponent,
        IndisponibiliteDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LaFactoryIndisponibiliteModule {}
