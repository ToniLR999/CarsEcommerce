import { Car } from "../car/car";
import { User } from "../user/user";

export class Review {

    id!: number;
    rating: number|undefined;
    comment: string|undefined;
    createdAt!: Date;
    user!: User;
    car!: Car;


}
