import { carCategory } from "../carCategory/carCategory";
import { Cart } from "../cart/cart";
import { Order } from "../order/order";
import { Review } from "../review/review";

export class Car {

    id!: number;
    name: string|undefined;
    description: string|undefined;
    price!: number;
    stock!: number;
    category!: carCategory;
    images: string[] = [];
    orders: Order[] = [];
    reviews: Review[] = [];
    carts: Cart[] = [];
}