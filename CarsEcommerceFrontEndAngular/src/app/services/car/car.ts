import { carCategory } from "../carCategory/carCategory";
import { Cart } from "../cart/cart";
import { Order } from "../order/order";
import { Review } from "../review/review";

export class Car {

    car_Id!: number;
    name: string|undefined;
    description: String|undefined;
    price!: number;
    stock!: number;
    category!: carCategory;
    images: String[] = [];
    orders: Order[] = [];
    reviews: Review[] = [];
    carts: Cart[] = [];
}