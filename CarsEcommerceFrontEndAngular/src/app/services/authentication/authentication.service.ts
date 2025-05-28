import { Injectable } from '@angular/core';
import { User } from '../user/user';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  public isLoggedIn = new BehaviorSubject<boolean>(this.isUserLoggedIn());
  private userRoleSubject = new BehaviorSubject<string>(this.getUserRole()); // Guardamos el rol aquí


    // Getter para obtener el estado del usuario logueado
    get isLoggedIn$() {
      return this.isLoggedIn.asObservable();
    }

  result: boolean = false;


  authenticate(username: string, password: string, user: User) {

    if (username === user.username && password === user.password) {
      sessionStorage.setItem('username', username)
      sessionStorage.setItem('userRole', user.role); // Guardamos el rol en sessionStorage
      this.isLoggedIn.next(true);  // Notificar que el usuario está logueado
      this.userRoleSubject.next(user.role); // Establecemos el rol del usuario
      this.result = true;
      return this.result;
    } else {
      this.isLoggedIn.next(false); // Notificar que el login falló
      this.userRoleSubject.next(''); // Reseteamos el rol
      this.result = false;
      return this.result;
    }


  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem('username')
    //console.log(!(user === null))
    return !(user === null)
  }

    // Obtenemos el rol del usuario desde sessionStorage
    getUserRole(): string {
      return sessionStorage.getItem('userRole') || ''; // Traemos el rol guardado en sessionStorage
    }

  logOut() {
    console.log("LogOut: "+sessionStorage.getItem('username'));
    sessionStorage.removeItem('username');
    sessionStorage.removeItem('userRole'); // Limpiamos el rol también
    this.userRoleSubject.next(''); // Reseteamos el rol
  }

  getUserName(): string {
    return sessionStorage.getItem('username') || '';
  }
}
