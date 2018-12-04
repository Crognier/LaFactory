import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LaFactorySharedModule } from 'app/shared';
import {
    SalleComponent,
    SalleDetailComponent,
    SalleUpdateComponent,
    SalleDeletePopupComponent,
    SalleDeleteDialogComponent,
    salleRoute,
    sallePopupRoute
} from './';

const ENTITY_STATES = [...salleRoute, ...sallePopupRoute];

@NgModule({
    imports: [LaFactorySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [SalleComponent, SalleDetailComponent, SalleUpdateComponent, SalleDeleteDialogComponent, SalleDeletePopupComponent],
    entryComponents: [SalleComponent, SalleUpdateComponent, SalleDeleteDialogComponent, SalleDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LaFactorySalleModule {}
