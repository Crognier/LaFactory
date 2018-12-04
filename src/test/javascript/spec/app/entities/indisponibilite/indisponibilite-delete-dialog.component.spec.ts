/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LaFactoryTestModule } from '../../../test.module';
import { IndisponibiliteDeleteDialogComponent } from 'app/entities/indisponibilite/indisponibilite-delete-dialog.component';
import { IndisponibiliteService } from 'app/entities/indisponibilite/indisponibilite.service';

describe('Component Tests', () => {
    describe('Indisponibilite Management Delete Component', () => {
        let comp: IndisponibiliteDeleteDialogComponent;
        let fixture: ComponentFixture<IndisponibiliteDeleteDialogComponent>;
        let service: IndisponibiliteService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LaFactoryTestModule],
                declarations: [IndisponibiliteDeleteDialogComponent]
            })
                .overrideTemplate(IndisponibiliteDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(IndisponibiliteDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IndisponibiliteService);
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
