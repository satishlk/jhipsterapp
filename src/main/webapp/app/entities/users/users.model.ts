import { UserType } from '../user-type';
import { City } from '../city';
import { State } from '../state';
import { Country } from '../country';
import { Location } from '../location';
export class Users {
    constructor(
        public id?: number,
        public userId?: number,
        public fullName?: string,
        public username?: string,
        public password?: string,
        public email?: string,
        public contactNo?: string,
        public address?: string,
        public pincode?: number,
        public activation?: boolean,
        public vendorId?: number,
        public addDatetime?: any,
        public addUserId?: string,
        public updateDateTime?: any,
        public updateUserId?: string,
        public parent?: number,
        public type?: UserType,
        public city?: City,
        public state?: State,
        public country?: Country,
        public location?: Location,
        public user?: Location,
    ) {
        this.activation = false;
    }
}
