import { NgModule } from '@angular/core';

import { LaFactorySharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [LaFactorySharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [LaFactorySharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class LaFactorySharedCommonModule {}
