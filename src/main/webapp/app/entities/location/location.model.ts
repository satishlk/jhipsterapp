import { City } from '../city';
import { State } from '../state';
import { Country } from '../country';
import { Users } from '../users';
export class Location {
    constructor(
        public id?: number,
        public locationId?: number,
        public locationName?: string,
        public locationAddress?: string,
        public locationContactNo?: string,
        public activation?: boolean,
        public vendorId?: number,
        public addDatetime?: any,
        public addUserId?: string,
        public updateDateTime?: any,
        public updateUserId?: string,
        public city?: City,
        public state?: State,
        public country?: Country,
        public users?: Users,
    ) {
        this.activation = false;
    }
}
