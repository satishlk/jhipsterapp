import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { Ionic2SampleTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { UserTypeDetailComponent } from '../../../../../../main/webapp/app/entities/user-type/user-type-detail.component';
import { UserTypeService } from '../../../../../../main/webapp/app/entities/user-type/user-type.service';
import { UserType } from '../../../../../../main/webapp/app/entities/user-type/user-type.model';

describe('Component Tests', () => {

    describe('UserType Management Detail Component', () => {
        let comp: UserTypeDetailComponent;
        let fixture: ComponentFixture<UserTypeDetailComponent>;
        let service: UserTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Ionic2SampleTestModule],
                declarations: [UserTypeDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    UserTypeService,
                    EventManager
                ]
            }).overrideComponent(UserTypeDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserTypeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new UserType(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.userType).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
