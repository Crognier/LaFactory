/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LaFactoryTestModule } from '../../../test.module';
import { FormateurMatiereComponent } from 'app/entities/formateur-matiere/formateur-matiere.component';
import { FormateurMatiereService } from 'app/entities/formateur-matiere/formateur-matiere.service';
import { FormateurMatiere } from 'app/shared/model/formateur-matiere.model';

describe('Component Tests', () => {
    describe('FormateurMatiere Management Component', () => {
        let comp: FormateurMatiereComponent;
        let fixture: ComponentFixture<FormateurMatiereComponent>;
        let service: FormateurMatiereService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LaFactoryTestModule],
                declarations: [FormateurMatiereComponent],
                providers: []
            })
                .overrideTemplate(FormateurMatiereComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FormateurMatiereComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FormateurMatiereService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new FormateurMatiere(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.formateurMatieres[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
