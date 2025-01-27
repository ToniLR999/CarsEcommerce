import { Cart } from "../cart/cart";
import { Order } from "../order/order";
import { Review } from "../review/review";
import { Role } from "../role/role";

export class User {
    id!: number;
    username!: string;
    email!: string;
    password!: string;
    registerDate!: Date;
    phoneNumber!: string;
    isActive!: boolean;
    role!: Role;
    orders: Order[] = [];
    reviews: Review[] = [];
    cart!: Cart;

}