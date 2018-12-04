import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LaFactorySharedModule } from 'app/shared';
import {
    VideoProjecteurComponent,
    VideoProjecteurDetailComponent,
    VideoProjecteurUpdateComponent,
    VideoProjecteurDeletePopupComponent,
    VideoProjecteurDeleteDialogComponent,
    videoProjecteurRoute,
    videoProjecteurPopupRoute
} from './';

const ENTITY_STATES = [...videoProjecteurRoute, ...videoProjecteurPopupRoute];

@NgModule({
    imports: [LaFactorySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        VideoProjecteurComponent,
        VideoProjecteurDetailComponent,
        VideoProjecteurUpdateComponent,
        VideoProjecteurDeleteDialogComponent,
        VideoProjecteurDeletePopupComponent
    ],
    entryComponents: [
        VideoProjecteurComponent,
        VideoProjecteurUpdateComponent,
        VideoProjecteurDeleteDialogComponent,
        VideoProjecteurDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LaFactoryVideoProjecteurModule {}
