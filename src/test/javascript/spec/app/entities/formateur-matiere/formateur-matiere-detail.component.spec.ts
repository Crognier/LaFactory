/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LaFactoryTestModule } from '../../../test.module';
import { FormateurMatiereDetailComponent } from 'app/entities/formateur-matiere/formateur-matiere-detail.component';
import { FormateurMatiere } from 'app/shared/model/formateur-matiere.model';

describe('Component Tests', () => {
    describe('FormateurMatiere Management Detail Component', () => {
        let comp: FormateurMatiereDetailComponent;
        let fixture: ComponentFixture<FormateurMatiereDetailComponent>;
        const route = ({ data: of({ formateurMatiere: new FormateurMatiere(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LaFactoryTestModule],
                declarations: [FormateurMatiereDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(FormateurMatiereDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FormateurMatiereDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.formateurMatiere).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
