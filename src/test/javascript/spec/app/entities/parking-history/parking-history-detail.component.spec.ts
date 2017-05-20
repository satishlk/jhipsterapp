import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { Ionic2SampleTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ParkingHistoryDetailComponent } from '../../../../../../main/webapp/app/entities/parking-history/parking-history-detail.component';
import { ParkingHistoryService } from '../../../../../../main/webapp/app/entities/parking-history/parking-history.service';
import { ParkingHistory } from '../../../../../../main/webapp/app/entities/parking-history/parking-history.model';

describe('Component Tests', () => {

    describe('ParkingHistory Management Detail Component', () => {
        let comp: ParkingHistoryDetailComponent;
        let fixture: ComponentFixture<ParkingHistoryDetailComponent>;
        let service: ParkingHistoryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Ionic2SampleTestModule],
                declarations: [ParkingHistoryDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ParkingHistoryService,
                    EventManager
                ]
            }).overrideComponent(ParkingHistoryDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ParkingHistoryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParkingHistoryService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ParkingHistory(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.parkingHistory).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
