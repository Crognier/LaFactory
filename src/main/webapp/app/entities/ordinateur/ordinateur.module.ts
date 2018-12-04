import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LaFactorySharedModule } from 'app/shared';
import {
    OrdinateurComponent,
    OrdinateurDetailComponent,
    OrdinateurUpdateComponent,
    OrdinateurDeletePopupComponent,
    OrdinateurDeleteDialogComponent,
    ordinateurRoute,
    ordinateurPopupRoute
} from './';

const ENTITY_STATES = [...ordinateurRoute, ...ordinateurPopupRoute];

@NgModule({
    imports: [LaFactorySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        OrdinateurComponent,
        OrdinateurDetailComponent,
        OrdinateurUpdateComponent,
        OrdinateurDeleteDialogComponent,
        OrdinateurDeletePopupComponent
    ],
    entryComponents: [OrdinateurComponent, OrdinateurUpdateComponent, OrdinateurDeleteDialogComponent, OrdinateurDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LaFactoryOrdinateurModule {}
