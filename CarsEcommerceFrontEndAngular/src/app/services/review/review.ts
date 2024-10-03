import { Car } from "../car/car";
import { User } from "../user/user";

export class Review {

    review_Id!: number;
    rating: number|undefined;
    comment: String|undefined;
    createdAt!: Date;
    user!: User;
    car!: Car;


}
