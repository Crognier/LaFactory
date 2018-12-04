import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LaFactorySharedModule } from 'app/shared';
import {
    FormateurComponent,
    FormateurDetailComponent,
    FormateurUpdateComponent,
    FormateurDeletePopupComponent,
    FormateurDeleteDialogComponent,
    formateurRoute,
    formateurPopupRoute
} from './';

const ENTITY_STATES = [...formateurRoute, ...formateurPopupRoute];

@NgModule({
    imports: [LaFactorySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FormateurComponent,
        FormateurDetailComponent,
        FormateurUpdateComponent,
        FormateurDeleteDialogComponent,
        FormateurDeletePopupComponent
    ],
    entryComponents: [FormateurComponent, FormateurUpdateComponent, FormateurDeleteDialogComponent, FormateurDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LaFactoryFormateurModule {}
