/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LaFactoryTestModule } from '../../../test.module';
import { OrdinateurDeleteDialogComponent } from 'app/entities/ordinateur/ordinateur-delete-dialog.component';
import { OrdinateurService } from 'app/entities/ordinateur/ordinateur.service';

describe('Component Tests', () => {
    describe('Ordinateur Management Delete Component', () => {
        let comp: OrdinateurDeleteDialogComponent;
        let fixture: ComponentFixture<OrdinateurDeleteDialogComponent>;
        let service: OrdinateurService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LaFactoryTestModule],
                declarations: [OrdinateurDeleteDialogComponent]
            })
                .overrideTemplate(OrdinateurDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OrdinateurDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrdinateurService);
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
