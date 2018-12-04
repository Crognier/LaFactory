/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { LaFactoryTestModule } from '../../../test.module';
import { StagiaireUpdateComponent } from 'app/entities/stagiaire/stagiaire-update.component';
import { StagiaireService } from 'app/entities/stagiaire/stagiaire.service';
import { Stagiaire } from 'app/shared/model/stagiaire.model';

describe('Component Tests', () => {
    describe('Stagiaire Management Update Component', () => {
        let comp: StagiaireUpdateComponent;
        let fixture: ComponentFixture<StagiaireUpdateComponent>;
        let service: StagiaireService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LaFactoryTestModule],
                declarations: [StagiaireUpdateComponent]
            })
                .overrideTemplate(StagiaireUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StagiaireUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StagiaireService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Stagiaire(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.stagiaire = entity;
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
                    const entity = new Stagiaire();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.stagiaire = entity;
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
