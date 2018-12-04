/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LaFactoryTestModule } from '../../../test.module';
import { OrdinateurComponent } from 'app/entities/ordinateur/ordinateur.component';
import { OrdinateurService } from 'app/entities/ordinateur/ordinateur.service';
import { Ordinateur } from 'app/shared/model/ordinateur.model';

describe('Component Tests', () => {
    describe('Ordinateur Management Component', () => {
        let comp: OrdinateurComponent;
        let fixture: ComponentFixture<OrdinateurComponent>;
        let service: OrdinateurService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LaFactoryTestModule],
                declarations: [OrdinateurComponent],
                providers: []
            })
                .overrideTemplate(OrdinateurComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OrdinateurComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrdinateurService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Ordinateur(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.ordinateurs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
