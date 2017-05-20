export class UserType {
    constructor(
        public id?: number,
        public userType?: string,
        public userDescrption?: string,
        public addDatetime?: any,
        public addUserId?: string,
        public updateDateTime?: any,
        public updateUserId?: string,
        public activation?: boolean,
    ) {
        this.activation = false;
    }
}
