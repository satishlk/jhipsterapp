import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { RateCard } from './rate-card.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class RateCardService {

    private resourceUrl = 'api/rate-cards';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(rateCard: RateCard): Observable<RateCard> {
        const copy: RateCard = Object.assign({}, rateCard);
        copy.addDatetime = this.dateUtils.toDate(rateCard.addDatetime);
        copy.updateDateTime = this.dateUtils.toDate(rateCard.updateDateTime);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(rateCard: RateCard): Observable<RateCard> {
        const copy: RateCard = Object.assign({}, rateCard);

        copy.addDatetime = this.dateUtils.toDate(rateCard.addDatetime);

        copy.updateDateTime = this.dateUtils.toDate(rateCard.updateDateTime);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<RateCard> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            jsonResponse.addDatetime = this.dateUtils
                .convertDateTimeFromServer(jsonResponse.addDatetime);
            jsonResponse.updateDateTime = this.dateUtils
                .convertDateTimeFromServer(jsonResponse.updateDateTime);
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
            jsonResponse[i].addDatetime = this.dateUtils
                .convertDateTimeFromServer(jsonResponse[i].addDatetime);
            jsonResponse[i].updateDateTime = this.dateUtils
                .convertDateTimeFromServer(jsonResponse[i].updateDateTime);
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
