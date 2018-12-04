import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { LaFactoryOrdinateurModule } from './ordinateur/ordinateur.module';
import { LaFactoryVideoProjecteurModule } from './video-projecteur/video-projecteur.module';
import { LaFactorySalleModule } from './salle/salle.module';
import { LaFactoryStagiaireModule } from './stagiaire/stagiaire.module';
import { LaFactoryGestionnaireModule } from './gestionnaire/gestionnaire.module';
import { LaFactoryTechnicienModule } from './technicien/technicien.module';
import { LaFactoryFormateurModule } from './formateur/formateur.module';
import { LaFactoryModuleModule } from './module/module.module';
import { LaFactoryCursusModule } from './cursus/cursus.module';
import { LaFactoryMatiereModule } from './matiere/matiere.module';
import { LaFactoryFormateurMatiereModule } from './formateur-matiere/formateur-matiere.module';
import { LaFactoryIndisponibiliteModule } from './indisponibilite/indisponibilite.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        LaFactoryOrdinateurModule,
        LaFactoryVideoProjecteurModule,
        LaFactorySalleModule,
        LaFactoryStagiaireModule,
        LaFactoryGestionnaireModule,
        LaFactoryTechnicienModule,
        LaFactoryFormateurModule,
        LaFactoryModuleModule,
        LaFactoryCursusModule,
        LaFactoryMatiereModule,
        LaFactoryFormateurMatiereModule,
        LaFactoryIndisponibiliteModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LaFactoryEntityModule {}
