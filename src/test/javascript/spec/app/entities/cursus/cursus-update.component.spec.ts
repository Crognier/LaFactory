/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { LaFactoryTestModule } from '../../../test.module';
import { CursusUpdateComponent } from 'app/entities/cursus/cursus-update.component';
import { CursusService } from 'app/entities/cursus/cursus.service';
import { Cursus } from 'app/shared/model/cursus.model';

describe('Component Tests', () => {
    describe('Cursus Management Update Component', () => {
        let comp: CursusUpdateComponent;
        let fixture: ComponentFixture<CursusUpdateComponent>;
        let service: CursusService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LaFactoryTestModule],
                declarations: [CursusUpdateComponent]
            })
                .overrideTemplate(CursusUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CursusUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CursusService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Cursus(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.cursus = entity;
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
                    const entity = new Cursus();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.cursus = entity;
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
