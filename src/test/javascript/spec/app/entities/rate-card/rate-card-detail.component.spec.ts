import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { Ionic2SampleTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RateCardDetailComponent } from '../../../../../../main/webapp/app/entities/rate-card/rate-card-detail.component';
import { RateCardService } from '../../../../../../main/webapp/app/entities/rate-card/rate-card.service';
import { RateCard } from '../../../../../../main/webapp/app/entities/rate-card/rate-card.model';

describe('Component Tests', () => {

    describe('RateCard Management Detail Component', () => {
        let comp: RateCardDetailComponent;
        let fixture: ComponentFixture<RateCardDetailComponent>;
        let service: RateCardService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Ionic2SampleTestModule],
                declarations: [RateCardDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RateCardService,
                    EventManager
                ]
            }).overrideComponent(RateCardDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RateCardDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RateCardService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RateCard(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.rateCard).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
