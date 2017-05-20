import { Country } from '../country';
import { City } from '../city';
import { Location } from '../location';
export class State {
    constructor(
        public id?: number,
        public stateId?: number,
        public stateName?: string,
        public stateCode?: string,
        public stateDescription?: string,
        public country?: Country,
        public state?: City,
        public location?: Location,
    ) {
    }
}
