import { Injectable } from '@angular/core';
import { User } from '../user/user';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  result: boolean = false;


  authenticate(username: string, password: string, user: User) {

    if (username === user.username && password === user.password) {
      sessionStorage.setItem('username', username)

      this.result = true;
      return this.result;
    } else {
      this.result = false;
      return this.result;
    }


  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem('username')
    //console.log(!(user === null))
    return !(user === null)
  }

  logOut() {
    sessionStorage.removeItem('username')
  }}
