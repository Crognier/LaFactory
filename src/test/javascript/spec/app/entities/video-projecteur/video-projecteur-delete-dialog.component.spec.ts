/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { LaFactoryTestModule } from '../../../test.module';
import { VideoProjecteurDeleteDialogComponent } from 'app/entities/video-projecteur/video-projecteur-delete-dialog.component';
import { VideoProjecteurService } from 'app/entities/video-projecteur/video-projecteur.service';

describe('Component Tests', () => {
    describe('VideoProjecteur Management Delete Component', () => {
        let comp: VideoProjecteurDeleteDialogComponent;
        let fixture: ComponentFixture<VideoProjecteurDeleteDialogComponent>;
        let service: VideoProjecteurService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LaFactoryTestModule],
                declarations: [VideoProjecteurDeleteDialogComponent]
            })
                .overrideTemplate(VideoProjecteurDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VideoProjecteurDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VideoProjecteurService);
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
