import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Car } from './car';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CarService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  // Método para formatear URLs de imágenes
  formatImageUrl(imageUrl: string): string {
    if (!imageUrl) return '';
    
    if (imageUrl.startsWith('http')) {
      // Codificar la URL para manejar caracteres especiales
      const encodedUrl = encodeURIComponent(imageUrl);
      return `${this.apiServerUrl}/external-images/${encodedUrl}`;
    }
    return imageUrl;
  }

  // Método para formatear todas las imágenes de un coche
  formatCarImages(car: Car): Car {
    if (car.images) {
      car.images = car.images.map(img => this.formatImageUrl(img));
    }
    return car;
  }
  
  public getCars(): Observable<Car[]> {
    return this.http.get<Car[]>(`${this.apiServerUrl}/car/all`).pipe(
      map(cars => cars.map(car => this.formatCarImages(car)))
    );
  }

  public getCategories(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiServerUrl}/category/all`);
  }
  
  public getCarbyId(id: number): Observable<Car> {
    return this.http.get<Car>(`${this.apiServerUrl}/car/find/${id}`).pipe(
      map(car => this.formatCarImages(car))
    );
  }

  public addCar(car: Car): Observable<Car> {
    return this.http.post<Car>(`${this.apiServerUrl}/car/add`, car);
  }

  public updateCar(car: Car): Observable<Car> {
    return this.http.put<Car>(`${this.apiServerUrl}/car/update`, car);
  }

  public DeleteCar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/car/delete/${id}`);
  }
}
