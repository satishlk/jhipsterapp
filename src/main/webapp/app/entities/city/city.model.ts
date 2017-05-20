import { State } from '../state';
import { Location } from '../location';
export class City {
    constructor(
        public id?: number,
        public cityId?: number,
        public cityName?: string,
        public cityCode?: string,
        public cityDescription?: string,
        public state?: State,
        public location?: Location,
    ) {
    }
}
