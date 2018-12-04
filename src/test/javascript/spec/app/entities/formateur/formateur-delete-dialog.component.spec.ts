/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LaFactoryTestModule } from '../../../test.module';
import { FormateurDeleteDialogComponent } from 'app/entities/formateur/formateur-delete-dialog.component';
import { FormateurService } from 'app/entities/formateur/formateur.service';

describe('Component Tests', () => {
    describe('Formateur Management Delete Component', () => {
        let comp: FormateurDeleteDialogComponent;
        let fixture: ComponentFixture<FormateurDeleteDialogComponent>;
        let service: FormateurService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LaFactoryTestModule],
                declarations: [FormateurDeleteDialogComponent]
            })
                .overrideTemplate(FormateurDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FormateurDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FormateurService);
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
