/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { LaFactoryTestModule } from '../../../test.module';
import { IndisponibiliteUpdateComponent } from 'app/entities/indisponibilite/indisponibilite-update.component';
import { IndisponibiliteService } from 'app/entities/indisponibilite/indisponibilite.service';
import { Indisponibilite } from 'app/shared/model/indisponibilite.model';

describe('Component Tests', () => {
    describe('Indisponibilite Management Update Component', () => {
        let comp: IndisponibiliteUpdateComponent;
        let fixture: ComponentFixture<IndisponibiliteUpdateComponent>;
        let service: IndisponibiliteService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LaFactoryTestModule],
                declarations: [IndisponibiliteUpdateComponent]
            })
                .overrideTemplate(IndisponibiliteUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(IndisponibiliteUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IndisponibiliteService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Indisponibilite(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.indisponibilite = entity;
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
                    const entity = new Indisponibilite();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.indisponibilite = entity;
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
