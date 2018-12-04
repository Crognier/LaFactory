/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LaFactoryTestModule } from '../../../test.module';
import { StagiaireDetailComponent } from 'app/entities/stagiaire/stagiaire-detail.component';
import { Stagiaire } from 'app/shared/model/stagiaire.model';

describe('Component Tests', () => {
    describe('Stagiaire Management Detail Component', () => {
        let comp: StagiaireDetailComponent;
        let fixture: ComponentFixture<StagiaireDetailComponent>;
        const route = ({ data: of({ stagiaire: new Stagiaire(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LaFactoryTestModule],
                declarations: [StagiaireDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(StagiaireDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StagiaireDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.stagiaire).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
