import { Component, ElementRef, OnInit, Renderer2, ViewChild } from '@angular/core';
import { User } from '../services/user/user';
import { AuthenticationService } from '../services/authentication/authentication.service';
import { UntypedFormGroup } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { CarService } from '../services/car/car.service';
import { UserService } from '../services/user/user.service';
import { OrderService } from '../services/order/order.service';
import { ReviewService } from '../services/review/review.service';
import { CartService } from '../services/cart/cart.service';

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrl: './admin-panel.component.css'
})
export class AdminPanelComponent implements OnInit{
  isUserLoggedIn: boolean = false;
  user: User;
  isCreateFormVisible: boolean = false; // Controla la visibilidad del formulario
  currentEntity: string = '';


  @ViewChild('createCarFormContainer') addCarFormContainer!: ElementRef;
  createCarForm!: UntypedFormGroup;


  tableHeaders: string[] = []; // Encabezados de la tabla
  paginatedRows: string[][] = []; // Filas mostradas en la página actual
  rows: string[][] = []; // Array de filas de la tabla
  rowsPerPage: number = 5; // Número de filas por página
  currentPage: number = 1; // Página actual
  pages: number[] = []; // Número de páginas
  maxVisiblePages: number = 5; // Máximo número de botones de página visibles

  constructor(private carService: CarService, private userService: UserService, private orderService: OrderService, private reviewService: ReviewService, private cartService: CartService, private renderer: Renderer2,public loginService: AuthenticationService, private http: HttpClient){
    this.user = new User();
    
  }

  ngOnInit(): void {
    /*this.loginService.isLoggedIn.subscribe((loggedIn) => {
      this.isUserLoggedIn = loggedIn;
      console.log("OnInit isUserLoggedIn: "+this.isUserLoggedIn);
    });*/  
  
    //this.getTableData('cars'); // Cargar datos en las filas
    this.calculatePages(); // Calcular el número de páginas
    this.displayTable(this.currentPage); // Mostrar la primera página
  
    // Mostrar la sección predeterminada (por ejemplo, 'cars')
    this.mostrarSeccion('cars');
  }

  ngAfterViewInit(): void {

        // Login form toggle
        const createCarBtn = document.querySelector('#createCar-btn');
        if (createCarBtn) {
          this.renderer.listen(createCarBtn, 'click', () => {
            this.addCarFormContainer.nativeElement.classList.toggle('active');
          });
        }
        const closeCreateForm = document.querySelector('#close-create-form');
        if (closeCreateForm) {
          this.renderer.listen(closeCreateForm, 'click', () => {
            this.addCarFormContainer.nativeElement.classList.remove('active');
          });
        }
    
  }



  createCar(){



  }

  // Método para mostrar la sección seleccionada
  mostrarSeccion(seccion: string): void {
  // Ocultar todas las secciones
  const secciones = document.querySelectorAll('.seccion') as NodeListOf<HTMLElement>;
  secciones.forEach((sec) => {
    sec.style.display = 'none';
  });

  // Mostrar la sección seleccionada
  const selectedSection = document.getElementById(seccion);
  if (selectedSection) {
    selectedSection.style.display = 'block';
  }
  this.getTableData(seccion);
}

openCreateForm(entity: string) {
  this.currentEntity = entity; // Almacenar la entidad
  this.isCreateFormVisible = true; // Mostrar el formulario
}


handleFormSubmit(formValue: any): void {
  console.log('Formulario recibido:', formValue);
  this.toggleCreateCarForm(); // Cierra el formulario después de enviarlo
}

handleFormClose(): void {
  this.toggleCreateCarForm(); // Cierra el formulario cuando se pulsa el botón de cierre
}


SignOut() {
  this.loginService.logOut();
  this.isUserLoggedIn = false;
}


getTableData(seccion: string): string[][] {
  // Añadimos más filas para probar la paginación
  console.log("Seccion: "+seccion);
  switch(seccion){
    case 'cars': {
      this.carService.getCars().subscribe(
        (data) => {
          // Mapear los datos del servicio para adaptarlos a la tabla
          this.rows = data.map(car => [
            String(car.id ?? '-'),   // Ajusta según las propiedades de `Car`
            car.name ?? 'N/A',
            car.description ?? 'N/A',
            String(car.category ?? ''),
            String(car.price ?? ''),
            String(car.stock ?? '')
          ]);
          this.tableHeaders = ['ID', 'Name', 'Description','Category', 'Price', 'Stock'];
          this.calculatePages();
          this.displayTable(this.currentPage);
        },
        (error) => {
          console.error('Error al obtener los autos:', error);
        }
      );
      break; // Agregar break para detener el flujo

    }
    case 'users': {
      this.userService.getUsers().subscribe(
        (data) => {
          // Mapear los datos del servicio para adaptarlos a la tabla
          this.rows = data.map(user => [
            String(user.id ?? '-'),   // Ajusta según las propiedades de `Car`
            user.username ?? 'N/A',
            user.email ?? 'N/A',
            String(user.registerDate ?? ''),
            String(user.phoneNumber ?? ''),
            String(user.role ?? '')
          ]);
          this.tableHeaders = ['ID', 'Username', 'Email','Register Date', 'Phone Number', 'Role'];
          this.calculatePages();
          this.displayTable(this.currentPage);
        },
        (error) => {
          console.error('Error al obtener los autos:', error);
        }
      );
      break; // Agregar break para detener el flujo
    }
    case 'orders': {
      this.orderService.getOrders().subscribe(
        (data) => {
          // Mapear los datos del servicio para adaptarlos a la tabla
          this.rows = data.map(order => [
            String(order.id ?? '-'),   // Ajusta según las propiedades de `Car`
            String(order.status ?? ''),
            String(order.totalPrice ?? ''),
            String(order.createdAt ?? '')
          ]);
          this.tableHeaders = ['Id', 'Status', 'Total Price','Created at'];
          this.calculatePages();
          this.displayTable(this.currentPage);
        },
        (error) => {
          console.error('Error al obtener los autos:', error);
        }
      );
      break; // Agregar break para detener el flujo
    }
    case 'reviews': {
      this.reviewService.getReviews().subscribe(
        (data) => {
          // Mapear los datos del servicio para adaptarlos a la tabla
          this.rows = data.map(review => [
            String(review.id ?? '-'),   // Ajusta según las propiedades de `Car`
            String(review.rating ?? ''),
            review.comment ?? 'N/A',
            String(review.createdAt ?? ''),
          ]);
          this.tableHeaders = ['Id', 'Raing', 'Comment','Created at'];
          this.calculatePages();
          this.displayTable(this.currentPage);
        },
        (error) => {
          console.error('Error al obtener los autos:', error);
        }
      );
      break; // Agregar break para detener el flujo
    }
    case 'carts': {
      this.cartService.getCarts().subscribe(
        (data) => {
          // Mapear los datos del servicio para adaptarlos a la tabla
          this.rows = data.map(cart => [
            String(cart.id ?? '-'),   // Ajusta según las propiedades de `Car`
            String(cart.user?.username ?? ''),
            cart.cars.map((car: any) => car.name).join(', ') ?? "-"// Extraer y concatenar nombres de los carros       
          ]);

          this.tableHeaders = ['Id', 'User', 'Cars'];
          this.calculatePages();
          this.displayTable(this.currentPage);
        },
        (error) => {
          console.error('Error al obtener los autos:', error);
        }
      );
      break; // Agregar break para detener el flujo
    }

  }


  return this.rows;

  /*return Array.from({ length: 50 }, (_, i) => [
    `Content ${i + 1}`,
    `Content ${i + 1}`,
    `Content ${i + 1}`,
    `Content ${i + 1}`,
    `Content ${i + 1}`
  ]);*/
}


displayTable(page: number): void {
  this.currentPage = page;
  const startIndex = (page - 1) * this.rowsPerPage;
  const endIndex = startIndex + this.rowsPerPage;
  this.paginatedRows = this.rows.slice(startIndex, endIndex);
}


calculatePages(): void {
  const totalPages = Math.ceil(this.rows.length / this.rowsPerPage);
  this.pages = Array.from({ length: totalPages }, (_, i) => i + 1);
}

changePage(page: number): void {
  this.displayTable(page);
}

previousPage(): void {
  if (this.currentPage > 1) {
    this.changePage(this.currentPage - 1);
  }
}

nextPage(): void {
  if (this.currentPage < this.pages.length) {
    this.changePage(this.currentPage + 1);
  }
}

// Método para obtener las páginas visibles en la paginación
getVisiblePages(): number[] {
  const halfRange = Math.floor(this.maxVisiblePages / 2);
  let start = Math.max(this.currentPage - halfRange, 1);
  let end = Math.min(start + this.maxVisiblePages - 1, this.pages.length);

  if (end - start < this.maxVisiblePages - 1) {
    start = Math.max(end - this.maxVisiblePages + 1, 1);
  }

  return this.pages.slice(start - 1, end);
}
toggleCreateCarForm(): void {
  this.isCreateFormVisible = !this.isCreateFormVisible; // Alterna la visibilidad
}

closeCreateForm() {
  this.isCreateFormVisible = false; // Cerrar el formulario
}





}
