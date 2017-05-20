import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { Ionic2SampleTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { UsersDetailComponent } from '../../../../../../main/webapp/app/entities/users/users-detail.component';
import { UsersService } from '../../../../../../main/webapp/app/entities/users/users.service';
import { Users } from '../../../../../../main/webapp/app/entities/users/users.model';

describe('Component Tests', () => {

    describe('Users Management Detail Component', () => {
        let comp: UsersDetailComponent;
        let fixture: ComponentFixture<UsersDetailComponent>;
        let service: UsersService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Ionic2SampleTestModule],
                declarations: [UsersDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    UsersService,
                    EventManager
                ]
            }).overrideComponent(UsersDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UsersDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UsersService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Users(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.users).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
