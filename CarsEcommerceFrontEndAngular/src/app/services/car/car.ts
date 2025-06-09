import { carCategory } from "../carCategory/carCategory";
import { Cart } from "../cart/cart";
import { Order } from "../order/order";
import { Review } from "../review/review";

export class Car {

    id!: number;
    marca: string|undefined;
    modelo: string|undefined;
    precio: number|undefined;
    año: number|undefined;
    categoria: string|undefined;
    combustible: string|undefined;
    transmision: string|undefined;
    kilometraje: number|undefined;
    disponible: boolean|undefined;
    descripcion: string|undefined;
    imagenUrl: string|undefined;
    images: string[] = [];
    orders: Order[] = [];
    reviews: Review[] = [];
    carts: Cart[] = [];
}