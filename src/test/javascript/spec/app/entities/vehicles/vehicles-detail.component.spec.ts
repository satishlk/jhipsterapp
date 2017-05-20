import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { Ionic2SampleTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { VehiclesDetailComponent } from '../../../../../../main/webapp/app/entities/vehicles/vehicles-detail.component';
import { VehiclesService } from '../../../../../../main/webapp/app/entities/vehicles/vehicles.service';
import { Vehicles } from '../../../../../../main/webapp/app/entities/vehicles/vehicles.model';

describe('Component Tests', () => {

    describe('Vehicles Management Detail Component', () => {
        let comp: VehiclesDetailComponent;
        let fixture: ComponentFixture<VehiclesDetailComponent>;
        let service: VehiclesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Ionic2SampleTestModule],
                declarations: [VehiclesDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    VehiclesService,
                    EventManager
                ]
            }).overrideComponent(VehiclesDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VehiclesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VehiclesService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Vehicles(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.vehicles).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
