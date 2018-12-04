/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LaFactoryTestModule } from '../../../test.module';
import { IndisponibiliteComponent } from 'app/entities/indisponibilite/indisponibilite.component';
import { IndisponibiliteService } from 'app/entities/indisponibilite/indisponibilite.service';
import { Indisponibilite } from 'app/shared/model/indisponibilite.model';

describe('Component Tests', () => {
    describe('Indisponibilite Management Component', () => {
        let comp: IndisponibiliteComponent;
        let fixture: ComponentFixture<IndisponibiliteComponent>;
        let service: IndisponibiliteService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LaFactoryTestModule],
                declarations: [IndisponibiliteComponent],
                providers: []
            })
                .overrideTemplate(IndisponibiliteComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(IndisponibiliteComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IndisponibiliteService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Indisponibilite(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.indisponibilites[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
