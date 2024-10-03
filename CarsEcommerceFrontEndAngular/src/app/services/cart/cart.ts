import { Car } from "../car/car";
import { User } from "../user/user";

export class Cart {

    cart_id!: number;
    user!: User;
    cars: Car[] = [];
}