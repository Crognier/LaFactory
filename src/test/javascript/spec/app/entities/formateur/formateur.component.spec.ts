/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LaFactoryTestModule } from '../../../test.module';
import { FormateurComponent } from 'app/entities/formateur/formateur.component';
import { FormateurService } from 'app/entities/formateur/formateur.service';
import { Formateur } from 'app/shared/model/formateur.model';

describe('Component Tests', () => {
    describe('Formateur Management Component', () => {
        let comp: FormateurComponent;
        let fixture: ComponentFixture<FormateurComponent>;
        let service: FormateurService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LaFactoryTestModule],
                declarations: [FormateurComponent],
                providers: []
            })
                .overrideTemplate(FormateurComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FormateurComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FormateurService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Formateur(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.formateurs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
