import { Cart } from "../cart/cart";
import { Order } from "../order/order";
import { Review } from "../review/review";

export class User {
    user_Id!: number;
    username: string|undefined;
    email: String|undefined;
    password!: String;
    register_date!: Date;
    phoneNumber!: String;
    isActive!: boolean;
    orders: Order[] = [];
    reviews: Review[] = [];
    cart!: Cart;

}