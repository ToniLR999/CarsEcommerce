import { Car } from "../car/car";
import { orderStatus } from "../OrderStatus/orderStatus";
import { User } from "../user/user";

export class Order {

    order_Id!: number;
    status!: orderStatus;
    totalPrice: number|undefined;
    createdAt: Date|undefined;
    cars: Car[] = [];
    user!: User;
}