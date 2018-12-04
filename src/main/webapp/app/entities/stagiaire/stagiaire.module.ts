import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LaFactorySharedModule } from 'app/shared';
import {
    StagiaireComponent,
    StagiaireDetailComponent,
    StagiaireUpdateComponent,
    StagiaireDeletePopupComponent,
    StagiaireDeleteDialogComponent,
    stagiaireRoute,
    stagiairePopupRoute
} from './';

const ENTITY_STATES = [...stagiaireRoute, ...stagiairePopupRoute];

@NgModule({
    imports: [LaFactorySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        StagiaireComponent,
        StagiaireDetailComponent,
        StagiaireUpdateComponent,
        StagiaireDeleteDialogComponent,
        StagiaireDeletePopupComponent
    ],
    entryComponents: [StagiaireComponent, StagiaireUpdateComponent, StagiaireDeleteDialogComponent, StagiaireDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LaFactoryStagiaireModule {}
