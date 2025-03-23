import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthenticationService } from '../services/authentication/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {

  constructor(private authService: AuthenticationService, private router: Router) {}

  canActivate(): boolean {
    const loggedIn = sessionStorage.getItem('username') !== null;

    const userRole = sessionStorage.getItem('userRole');
    
   /** if (!loggedIn) {
      console.log("Acceso denegado: No hay sesión activa.");
    }
    return loggedIn;*/

    if (this.authService.isLoggedIn && userRole === 'ADMIN') {
      // El usuario tiene permiso para acceder
      return true;
    } else {
      // Si no tiene acceso, redirige al login
      this.router.navigate(['/home']);
      return false;
    }
  }
}
