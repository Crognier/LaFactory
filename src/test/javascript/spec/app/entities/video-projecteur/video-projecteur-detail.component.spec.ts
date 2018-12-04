/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LaFactoryTestModule } from '../../../test.module';
import { VideoProjecteurDetailComponent } from 'app/entities/video-projecteur/video-projecteur-detail.component';
import { VideoProjecteur } from 'app/shared/model/video-projecteur.model';

describe('Component Tests', () => {
    describe('VideoProjecteur Management Detail Component', () => {
        let comp: VideoProjecteurDetailComponent;
        let fixture: ComponentFixture<VideoProjecteurDetailComponent>;
        const route = ({ data: of({ videoProjecteur: new VideoProjecteur(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LaFactoryTestModule],
                declarations: [VideoProjecteurDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(VideoProjecteurDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VideoProjecteurDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.videoProjecteur).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
