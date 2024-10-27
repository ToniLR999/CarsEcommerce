import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { User  } from './user';
import { Cart } from '../cart/cart';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getUsers(): Observable<User[]>{
    return this.http.get<User[]>(`${this.apiServerUrl}/user/all`);

  }

  public getRoles(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiServerUrl}/role/all`);
  }

  public  loginUser(user: User): Observable<User> {
    return this.http.post<User>(`${this.apiServerUrl}/user/login`,user);
	}

  
  public getUserbyId(id: number): Observable<User>{
    return this.http.get<User>(`${this.apiServerUrl}/user/find/${id}`);

  }

  public  getUserByUsername(username: String): Observable<User> {
    return this.http.get<User>(`${this.apiServerUrl}/user/findByUsername/${username}`);
	}


  public addUser(user: User): Observable<User>{
    return this.http.post<User>(`${this.apiServerUrl}/user/add`, user);

  }

  public updateUser(user: User): Observable<User>{
    return this.http.put<User>(`${this.apiServerUrl}/user/update`, user);
  }

  public DeleteUser(id: number): Observable<void>{
    return this.http.delete<void>(`${this.apiServerUrl}/user/delete/${id}`);

  }
}
