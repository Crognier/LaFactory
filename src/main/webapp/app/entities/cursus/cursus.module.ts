import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LaFactorySharedModule } from 'app/shared';
import {
    CursusComponent,
    CursusDetailComponent,
    CursusUpdateComponent,
    CursusDeletePopupComponent,
    CursusDeleteDialogComponent,
    cursusRoute,
    cursusPopupRoute,
    CursusService
} from './';
import { GestionnaireService } from 'app/entities/gestionnaire';
import { ModuleService } from 'app/entities/module';
import { StagiaireService } from 'app/entities/stagiaire';

const ENTITY_STATES = [...cursusRoute, ...cursusPopupRoute];

@NgModule({
    providers: [CursusService, GestionnaireService, ModuleService, StagiaireService],
    imports: [LaFactorySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [CursusComponent, CursusDetailComponent, CursusUpdateComponent, CursusDeleteDialogComponent, CursusDeletePopupComponent],
    entryComponents: [CursusComponent, CursusUpdateComponent, CursusDeleteDialogComponent, CursusDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LaFactoryCursusModule {}
