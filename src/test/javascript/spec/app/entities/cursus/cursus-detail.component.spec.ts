/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LaFactoryTestModule } from '../../../test.module';
import { CursusDetailComponent } from 'app/entities/cursus/cursus-detail.component';
import { Cursus } from 'app/shared/model/cursus.model';

describe('Component Tests', () => {
    describe('Cursus Management Detail Component', () => {
        let comp: CursusDetailComponent;
        let fixture: ComponentFixture<CursusDetailComponent>;
        const route = ({ data: of({ cursus: new Cursus(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LaFactoryTestModule],
                declarations: [CursusDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CursusDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CursusDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.cursus).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
