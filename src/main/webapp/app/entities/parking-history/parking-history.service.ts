import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { ParkingHistory } from './parking-history.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class ParkingHistoryService {

    private resourceUrl = 'api/parking-histories';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(parkingHistory: ParkingHistory): Observable<ParkingHistory> {
        const copy: ParkingHistory = Object.assign({}, parkingHistory);
        copy.intime = this.dateUtils.toDate(parkingHistory.intime);
        copy.outTime = this.dateUtils.toDate(parkingHistory.outTime);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(parkingHistory: ParkingHistory): Observable<ParkingHistory> {
        const copy: ParkingHistory = Object.assign({}, parkingHistory);

        copy.intime = this.dateUtils.toDate(parkingHistory.intime);

        copy.outTime = this.dateUtils.toDate(parkingHistory.outTime);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<ParkingHistory> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            jsonResponse.intime = this.dateUtils
                .convertDateTimeFromServer(jsonResponse.intime);
            jsonResponse.outTime = this.dateUtils
                .convertDateTimeFromServer(jsonResponse.outTime);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: any) => this.convertResponse(res))
        ;
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: any): any {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            jsonResponse[i].intime = this.dateUtils
                .convertDateTimeFromServer(jsonResponse[i].intime);
            jsonResponse[i].outTime = this.dateUtils
                .convertDateTimeFromServer(jsonResponse[i].outTime);
        }
        res._body = jsonResponse;
        return res;
    }

    private createRequestOption(req?: any): BaseRequestOptions {
        const options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            const params: URLSearchParams = new URLSearchParams();
            params.set('page', req.page);
            params.set('size', req.size);
            if (req.sort) {
                params.paramsMap.set('sort', req.sort);
            }
            params.set('query', req.query);

            options.search = params;
        }
        return options;
    }
}
