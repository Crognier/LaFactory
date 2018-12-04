/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { OrdinateurService } from 'app/entities/ordinateur/ordinateur.service';
import { IOrdinateur, Ordinateur } from 'app/shared/model/ordinateur.model';

describe('Service Tests', () => {
    describe('Ordinateur Service', () => {
        let injector: TestBed;
        let service: OrdinateurService;
        let httpMock: HttpTestingController;
        let elemDefault: IOrdinateur;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(OrdinateurService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Ordinateur(0, 0, 0, 'AAAAAAA', 0, 0, currentDate);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        achat: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Ordinateur', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        achat: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        achat: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Ordinateur(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Ordinateur', async () => {
                const returnedFromService = Object.assign(
                    {
                        code: 1,
                        coutParJour: 1,
                        processeur: 'BBBBBB',
                        ram: 1,
                        hdd: 1,
                        achat: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        achat: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Ordinateur', async () => {
                const returnedFromService = Object.assign(
                    {
                        code: 1,
                        coutParJour: 1,
                        processeur: 'BBBBBB',
                        ram: 1,
                        hdd: 1,
                        achat: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        achat: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Ordinateur', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
