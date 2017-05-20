import { Vehicles } from '../vehicles';
export class RateCard {
    constructor(
        public id?: number,
        public ratePerHour?: number,
        public extraHour?: number,
        public vendorId?: number,
        public addDatetime?: any,
        public addUserId?: string,
        public updateDateTime?: any,
        public updateUserId?: string,
        public activation?: boolean,
        public vehicle?: Vehicles,
        public vehicles?: Vehicles,
    ) {
        this.activation = false;
    }
}
