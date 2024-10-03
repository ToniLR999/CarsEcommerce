import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { Car } from '../car/car';
import { Order } from './order';


@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getOrders(): Observable<Order[]>{
    return this.http.get<Order[]>(`${this.apiServerUrl}/order/all`);

  }
  
  public getOrderbyId(id: number): Observable<Order>{
    return this.http.get<Order>(`${this.apiServerUrl}/order/find/${id}`);

  }


  public addOrder(order: Order): Observable<Order>{
    return this.http.post<Order>(`${this.apiServerUrl}/order/add`, order);

  }

  public updateOrder(order: Order): Observable<Order>{
    return this.http.put<Order>(`${this.apiServerUrl}/order/update`, order);
  }

  public DeleteOrder(id: number): Observable<void>{
    return this.http.delete<void>(`${this.apiServerUrl}/order/delete/${id}`);

  }
}
