/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { LaFactoryTestModule } from '../../../test.module';
import { FormateurUpdateComponent } from 'app/entities/formateur/formateur-update.component';
import { FormateurService } from 'app/entities/formateur/formateur.service';
import { Formateur } from 'app/shared/model/formateur.model';

describe('Component Tests', () => {
    describe('Formateur Management Update Component', () => {
        let comp: FormateurUpdateComponent;
        let fixture: ComponentFixture<FormateurUpdateComponent>;
        let service: FormateurService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LaFactoryTestModule],
                declarations: [FormateurUpdateComponent]
            })
                .overrideTemplate(FormateurUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FormateurUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FormateurService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Formateur(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.formateur = entity;
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
                    const entity = new Formateur();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.formateur = entity;
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
