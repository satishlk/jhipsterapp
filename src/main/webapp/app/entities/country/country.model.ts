import { State } from '../state';
import { Location } from '../location';
export class Country {
    constructor(
        public id?: number,
        public countryId?: number,
        public countryName?: string,
        public countryCode?: string,
        public countryDescription?: string,
        public country?: State,
        public location?: Location,
    ) {
    }
}
