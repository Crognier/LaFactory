/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { LaFactoryTestModule } from '../../../test.module';
import { VideoProjecteurUpdateComponent } from 'app/entities/video-projecteur/video-projecteur-update.component';
import { VideoProjecteurService } from 'app/entities/video-projecteur/video-projecteur.service';
import { VideoProjecteur } from 'app/shared/model/video-projecteur.model';

describe('Component Tests', () => {
    describe('VideoProjecteur Management Update Component', () => {
        let comp: VideoProjecteurUpdateComponent;
        let fixture: ComponentFixture<VideoProjecteurUpdateComponent>;
        let service: VideoProjecteurService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LaFactoryTestModule],
                declarations: [VideoProjecteurUpdateComponent]
            })
                .overrideTemplate(VideoProjecteurUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(VideoProjecteurUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VideoProjecteurService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new VideoProjecteur(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.videoProjecteur = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new VideoProjecteur();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.videoProjecteur = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
