export class Vehicles {
    constructor(
        public id?: number,
        public vehicleId?: number,
        public vehiclesType?: string,
        public vehiclesDescrtion?: string,
        public addDatetime?: any,
        public addUserId?: string,
        public updateDateTime?: any,
        public updateUserId?: string,
        public activation?: boolean,
    ) {
        this.activation = false;
    }
}
