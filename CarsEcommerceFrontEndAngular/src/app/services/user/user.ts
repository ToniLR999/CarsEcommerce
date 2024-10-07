import { Cart } from "../cart/cart";
import { Order } from "../order/order";
import { Review } from "../review/review";
import { Role } from "../role/role";

export class User {
    user_Id!: number;
    username!: String;
    email!: String;
    password!: String;
    register_date!: Date;
    phoneNumber!: String;
    isActive!: boolean;
    role!: Role;
    orders: Order[] = [];
    reviews: Review[] = [];
    cart!: Cart;

}