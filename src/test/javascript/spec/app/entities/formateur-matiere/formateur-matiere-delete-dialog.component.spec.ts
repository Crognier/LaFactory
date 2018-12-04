/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LaFactoryTestModule } from '../../../test.module';
import { FormateurMatiereDeleteDialogComponent } from 'app/entities/formateur-matiere/formateur-matiere-delete-dialog.component';
import { FormateurMatiereService } from 'app/entities/formateur-matiere/formateur-matiere.service';

describe('Component Tests', () => {
    describe('FormateurMatiere Management Delete Component', () => {
        let comp: FormateurMatiereDeleteDialogComponent;
        let fixture: ComponentFixture<FormateurMatiereDeleteDialogComponent>;
        let service: FormateurMatiereService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LaFactoryTestModule],
                declarations: [FormateurMatiereDeleteDialogComponent]
            })
                .overrideTemplate(FormateurMatiereDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FormateurMatiereDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FormateurMatiereService);
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
