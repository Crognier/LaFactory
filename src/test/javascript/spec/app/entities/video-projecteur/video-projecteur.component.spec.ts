/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LaFactoryTestModule } from '../../../test.module';
import { VideoProjecteurComponent } from 'app/entities/video-projecteur/video-projecteur.component';
import { VideoProjecteurService } from 'app/entities/video-projecteur/video-projecteur.service';
import { VideoProjecteur } from 'app/shared/model/video-projecteur.model';

describe('Component Tests', () => {
    describe('VideoProjecteur Management Component', () => {
        let comp: VideoProjecteurComponent;
        let fixture: ComponentFixture<VideoProjecteurComponent>;
        let service: VideoProjecteurService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LaFactoryTestModule],
                declarations: [VideoProjecteurComponent],
                providers: []
            })
                .overrideTemplate(VideoProjecteurComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(VideoProjecteurComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VideoProjecteurService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new VideoProjecteur(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.videoProjecteurs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
