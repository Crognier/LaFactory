/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LaFactoryTestModule } from '../../../test.module';
import { GestionnaireDeleteDialogComponent } from 'app/entities/gestionnaire/gestionnaire-delete-dialog.component';
import { GestionnaireService } from 'app/entities/gestionnaire/gestionnaire.service';

describe('Component Tests', () => {
    describe('Gestionnaire Management Delete Component', () => {
        let comp: GestionnaireDeleteDialogComponent;
        let fixture: ComponentFixture<GestionnaireDeleteDialogComponent>;
        let service: GestionnaireService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LaFactoryTestModule],
                declarations: [GestionnaireDeleteDialogComponent]
            })
                .overrideTemplate(GestionnaireDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GestionnaireDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GestionnaireService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
