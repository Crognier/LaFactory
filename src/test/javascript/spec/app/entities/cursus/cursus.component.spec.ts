/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LaFactoryTestModule } from '../../../test.module';
import { CursusComponent } from 'app/entities/cursus/cursus.component';
import { CursusService } from 'app/entities/cursus/cursus.service';
import { Cursus } from 'app/shared/model/cursus.model';

describe('Component Tests', () => {
    describe('Cursus Management Component', () => {
        let comp: CursusComponent;
        let fixture: ComponentFixture<CursusComponent>;
        let service: CursusService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LaFactoryTestModule],
                declarations: [CursusComponent],
                providers: []
            })
                .overrideTemplate(CursusComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CursusComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CursusService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Cursus(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.cursuses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
