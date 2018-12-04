/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LaFactoryTestModule } from '../../../test.module';
import { StagiaireComponent } from 'app/entities/stagiaire/stagiaire.component';
import { StagiaireService } from 'app/entities/stagiaire/stagiaire.service';
import { Stagiaire } from 'app/shared/model/stagiaire.model';

describe('Component Tests', () => {
    describe('Stagiaire Management Component', () => {
        let comp: StagiaireComponent;
        let fixture: ComponentFixture<StagiaireComponent>;
        let service: StagiaireService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LaFactoryTestModule],
                declarations: [StagiaireComponent],
                providers: []
            })
                .overrideTemplate(StagiaireComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StagiaireComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StagiaireService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Stagiaire(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.stagiaires[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
