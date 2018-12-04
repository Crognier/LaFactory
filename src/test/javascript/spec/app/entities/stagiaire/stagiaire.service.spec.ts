/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { StagiaireService } from 'app/entities/stagiaire/stagiaire.service';
import { IStagiaire, Stagiaire, Niveau } from 'app/shared/model/stagiaire.model';

describe('Service Tests', () => {
    describe('Stagiaire Service', () => {
        let injector: TestBed;
        let service: StagiaireService;
        let httpMock: HttpTestingController;
        let elemDefault: IStagiaire;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(StagiaireService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new Stagiaire(0, 'AAAAAAA', 'AAAAAAA', 0, 'AAAAAAA', 0, 'AAAAAAA', 0, 'AAAAAAA', Niveau.DEBUTANT);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign({}, elemDefault);
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Stagiaire', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new Stagiaire(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Stagiaire', async () => {
                const returnedFromService = Object.assign(
                    {
                        nom: 'BBBBBB',
                        prenom: 'BBBBBB',
                        numero: 1,
                        rue: 'BBBBBB',
                        codePostal: 1,
                        ville: 'BBBBBB',
                        numeroTelephone: 1,
                        eMail: 'BBBBBB',
                        niveau: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Stagiaire', async () => {
                const returnedFromService = Object.assign(
                    {
                        nom: 'BBBBBB',
                        prenom: 'BBBBBB',
                        numero: 1,
                        rue: 'BBBBBB',
                        codePostal: 1,
                        ville: 'BBBBBB',
                        numeroTelephone: 1,
                        eMail: 'BBBBBB',
                        niveau: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
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

            it('should delete a Stagiaire', async () => {
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
