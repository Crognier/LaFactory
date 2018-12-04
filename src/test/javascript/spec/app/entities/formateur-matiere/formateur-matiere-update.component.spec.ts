/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { LaFactoryTestModule } from '../../../test.module';
import { FormateurMatiereUpdateComponent } from 'app/entities/formateur-matiere/formateur-matiere-update.component';
import { FormateurMatiereService } from 'app/entities/formateur-matiere/formateur-matiere.service';
import { FormateurMatiere } from 'app/shared/model/formateur-matiere.model';

describe('Component Tests', () => {
    describe('FormateurMatiere Management Update Component', () => {
        let comp: FormateurMatiereUpdateComponent;
        let fixture: ComponentFixture<FormateurMatiereUpdateComponent>;
        let service: FormateurMatiereService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LaFactoryTestModule],
                declarations: [FormateurMatiereUpdateComponent]
            })
                .overrideTemplate(FormateurMatiereUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FormateurMatiereUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FormateurMatiereService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new FormateurMatiere(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.formateurMatiere = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new FormateurMatiere();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.formateurMatiere = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
