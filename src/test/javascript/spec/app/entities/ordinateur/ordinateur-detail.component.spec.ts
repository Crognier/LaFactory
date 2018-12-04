/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LaFactoryTestModule } from '../../../test.module';
import { OrdinateurDetailComponent } from 'app/entities/ordinateur/ordinateur-detail.component';
import { Ordinateur } from 'app/shared/model/ordinateur.model';

describe('Component Tests', () => {
    describe('Ordinateur Management Detail Component', () => {
        let comp: OrdinateurDetailComponent;
        let fixture: ComponentFixture<OrdinateurDetailComponent>;
        const route = ({ data: of({ ordinateur: new Ordinateur(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LaFactoryTestModule],
                declarations: [OrdinateurDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(OrdinateurDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OrdinateurDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.ordinateur).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
