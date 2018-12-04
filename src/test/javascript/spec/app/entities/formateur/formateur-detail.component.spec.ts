/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LaFactoryTestModule } from '../../../test.module';
import { FormateurDetailComponent } from 'app/entities/formateur/formateur-detail.component';
import { Formateur } from 'app/shared/model/formateur.model';

describe('Component Tests', () => {
    describe('Formateur Management Detail Component', () => {
        let comp: FormateurDetailComponent;
        let fixture: ComponentFixture<FormateurDetailComponent>;
        const route = ({ data: of({ formateur: new Formateur(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LaFactoryTestModule],
                declarations: [FormateurDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(FormateurDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FormateurDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.formateur).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
