export class ParkingHistory {
    constructor(
        public id?: number,
        public parkingId?: number,
        public vehicleNo?: string,
        public contactNo?: string,
        public intime?: any,
        public outTime?: any,
        public inUser?: string,
        public outUser?: string,
        public cost?: number,
    ) {
    }
}
