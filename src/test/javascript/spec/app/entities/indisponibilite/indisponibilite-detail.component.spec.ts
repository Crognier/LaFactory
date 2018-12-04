/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LaFactoryTestModule } from '../../../test.module';
import { IndisponibiliteDetailComponent } from 'app/entities/indisponibilite/indisponibilite-detail.component';
import { Indisponibilite } from 'app/shared/model/indisponibilite.model';

describe('Component Tests', () => {
    describe('Indisponibilite Management Detail Component', () => {
        let comp: IndisponibiliteDetailComponent;
        let fixture: ComponentFixture<IndisponibiliteDetailComponent>;
        const route = ({ data: of({ indisponibilite: new Indisponibilite(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LaFactoryTestModule],
                declarations: [IndisponibiliteDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(IndisponibiliteDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(IndisponibiliteDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.indisponibilite).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
