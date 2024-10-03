import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Cart } from './cart';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private apiServerUrl = environment.apiBaseUrl;


  constructor(private http: HttpClient) { }

  
  public getCarts(): Observable<Cart[]>{
    return this.http.get<Cart[]>(`${this.apiServerUrl}/cart/all`);

  }
  
  public getCartbyId(id: number): Observable<Cart>{
    return this.http.get<Cart>(`${this.apiServerUrl}/cart/find/${id}`);

  }


  public addCart(cart: Cart): Observable<Cart>{
    return this.http.post<Cart>(`${this.apiServerUrl}/cart/add`, cart);

  }

  public updateCart(cart: Cart): Observable<Cart>{
    return this.http.put<Cart>(`${this.apiServerUrl}/cart/update`, cart);
  }

  public DeleteCart(id: number): Observable<void>{
    return this.http.delete<void>(`${this.apiServerUrl}/cart/delete/${id}`);

  }
}
