/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { LaFactoryTestModule } from '../../../test.module';
import { OrdinateurUpdateComponent } from 'app/entities/ordinateur/ordinateur-update.component';
import { OrdinateurService } from 'app/entities/ordinateur/ordinateur.service';
import { Ordinateur } from 'app/shared/model/ordinateur.model';

describe('Component Tests', () => {
    describe('Ordinateur Management Update Component', () => {
        let comp: OrdinateurUpdateComponent;
        let fixture: ComponentFixture<OrdinateurUpdateComponent>;
        let service: OrdinateurService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LaFactoryTestModule],
                declarations: [OrdinateurUpdateComponent]
            })
                .overrideTemplate(OrdinateurUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OrdinateurUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrdinateurService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Ordinateur(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.ordinateur = entity;
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
                    const entity = new Ordinateur();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.ordinateur = entity;
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
