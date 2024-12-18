import { ChangeDetectorRef, ChangeDetectionStrategy, AfterViewInit, Component, ElementRef, EventEmitter, Input, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { User } from '../services/user/user';
import { UserService } from '../services/user/user.service';
import { ReviewService } from '../services/review/review.service';
import { CarService } from '../services/car/car.service';
import { CartService } from '../services/cart/cart.service';
import { OrderService } from '../services/order/order.service';
import { Car } from '../services/car/car';
import { forkJoin } from 'rxjs';
import { Order } from '../services/order/order';
import { Review } from '../services/review/review';
import { Cart } from '../services/cart/cart';
import {FormControl, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatSelectModule} from '@angular/material/select';
import {MatFormFieldModule} from '@angular/material/form-field';


@Component({
  selector: 'app-admin-panel-section',
  templateUrl: './admin-panel-section.component.html',
  styleUrl: './admin-panel-section.component.css'
})
export class AdminPanelSectionComponent implements OnInit{
  @Input() entity: string = '';
  @Output() formSubmit = new EventEmitter<any>();
  @Output() closeForm = new EventEmitter<void>();

  toppings = new FormControl('');
  toppingList: string[] = ['Extra cheese', 'Mushroom', 'Onion', 'Pepperoni', 'Sausage', 'Tomato'];
  
  createForm!: UntypedFormGroup;
  formFields: any[] = []; // Dinámico: lista de campos

  isOptionsVisible: { [key: string]: boolean } = {};
  selectedOptions: string[] = [];

  carBrands: string[] = ['Toyota', 'Honda', 'Ford', 'BMW', 'Audi', 'Mercedes'];
  cars: Car[] = [];  // Para almacenar la lista de coches desde la API
  users: User[] = [];  // Para almacenar la lista de users desde la API
  categories: string[] = [];  // Almacena las categorías del enum
  roles: string[] = [];  // Almacena las categorías del enum
  images: string[] = [];  // Almacena las categorías del enum
  orders: Order[] = [];  // Almacena las categorías del enum
  reviews: Review[] = [];  // Almacena las categorías del enum
  carts: Cart[] = [];  // Almacena las categorías del enum
  orderStatuses: string[] = [];  // Almacena las categorías del enum

  constructor(
    private fb: UntypedFormBuilder, 
    private userService: UserService, 
    private carService: CarService, 
    private reviewService: ReviewService, 
    private cartService: CartService, 
    private orderService: OrderService,
    private cdRef: ChangeDetectorRef
  ) 
    {}


  ngOnInit() {
    this.loadOptionsData();  // Cargamos los coches al inicializar el componente
    //this.buildForm(); // Construimos el formulario la primera vez


  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['entity']) {
      this.buildForm(); // Volvemos a construir el formulario si cambia la entidad
    }
  }
  

  buildForm() {
    if (this.entity === 'Cars') {
      this.formFields = [
        { name: 'name', type: 'text', placeholder: 'Car Name', errors: [] },
        { name: 'description', type: 'text', placeholder: 'Description', errors: [] },
        { name: 'price', type: 'text', placeholder: 'Price', errors: [] },
        { name: 'stock', type: 'text', placeholder: 'Stock', errors: [] },
        { name: 'category', type: 'select', placeholder: 'Select a Category', errors: [], options: this.categories.map(category => category)  },
        { name: 'images', type: 'select-multiple', placeholder: 'Select Images', errors: [], options: this.images  },
        { name: 'orders', type: 'select-multiple', placeholder: 'Select Orders', errors: [], options: this.orders  },
        { name: 'reviews', type: 'select-multiple', placeholder: 'Select Reviews', errors: [], options: this.reviews  },
        { name: 'carts', type: 'select-multiple', placeholder: 'Select Carts', errors: [], options: this.carts  },
      ];

    // Imprimir las opciones de cada campo para revisar su contenido
    this.formFields.forEach(field => {
      console.log(`Field: ${field.name}, Options:`, field.options);
    });
    console.log("Lista: "+this.toppingList);
            // Inicializar el formulario reactivo con controles
    this.createForm = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      price: ['', Validators.required],
      stock: ['', Validators.required],
      category: ['', Validators.required],
      images: [[], [Validators.required]],
      orders: [[], [Validators.required]],
      reviews: [[], [Validators.required]], 
      carts: [[], [Validators.required]]
    });
    } else if (this.entity === 'Users') {

      this.formFields = [
        { name: 'username', type: 'text', placeholder: 'Username', errors: [] },
        { name: 'email', type: 'email', placeholder: 'Email', errors: [] },
        { name: 'phoneNumber', type: 'text', placeholder: 'PhoneNumber', errors: [] },
        { name: 'password', type: 'text', placeholder: 'Password', errors: []  },
        { name: 'role', type: 'select', placeholder: 'Select Role', errors: [], options: this.roles.map(role => role)  },
        { name: 'orders', type: 'select-multiple', placeholder: 'Select Orders', errors: [], options: this.orders  },
        { name: 'reviews', type: 'select-multiple', placeholder: 'Select Reviews', errors: [], options: this.reviews  },
        { name: 'cart', type: 'select', placeholder: 'Select Cart', errors: [], options: this.carts }
      ];

      this.createForm = this.fb.group({
        username: ['', Validators.required],
        email: ['', [Validators.required, Validators.email]],
        phoneNumber: ['', [Validators.pattern('[0-9]{3}-[0-9]{3}-[0-9]{4}')]], 
        password: ['', Validators.required],
        role: ['', Validators.required],
        orders: [[], [Validators.required]], // Número de teléfono
        reviews: [[], [Validators.required]], // Número de teléfono
        cart: ['', [Validators.required]]
      
      
      });
    }
      else if (this.entity === 'Orders') {

        this.formFields = [
          { name: 'totalPrice', type: 'text', placeholder: 'Total Price', errors: [] },
          { name: 'status', type: 'select', placeholder: 'Select Status', errors: [],   options: this.orderStatuses.map(status => status) },
          { name: 'cars', type: 'select-multiple', placeholder: 'Select Car', errors: [], options: this.cars  },
          { name: 'user', type: 'select', placeholder: 'Select User', errors: [],options: this.users   }
        ];
        this.createForm = this.fb.group({
          totalPrice: ['', Validators.required],        // Nombre de usuario
          status: ['', [Validators.required]],  // Correo electrónico
          cars: [[], [Validators.required]], // Número de teléfono
          user: ['', Validators.required]     // Contraseña
        });
    }
    else if (this.entity === 'Reviews') {
      
      this.formFields = [
        { name: 'rating', type: 'text', placeholder: 'Rating', errors: [] },
        { name: 'comment', type: 'text', placeholder: 'Comment', errors: [] },
        { name: 'user', type: 'select', placeholder: 'Select an User', errors: [],options: this.users },
        { name: 'car', type: 'select', placeholder: 'Select Car', errors: [], options: this.cars }
      ];
      this.createForm = this.fb.group({
        rating: ['', Validators.required],      
        comment: ['', [Validators.required, Validators.email]],  
        user: ['', [Validators.required]],
        car: ['', Validators.required]     
      });
    }
    else if (this.entity === 'Carts') {

      
      this.formFields = [
        { name: 'user', type: 'select', placeholder: 'Select an User', errors: [],options: this.users },  
        { name: 'cars', type: 'select-multiple', placeholder: 'Select Cars', errors: [], options: this.cars }
      ];
      this.createForm = this.fb.group({
        user: ['', Validators.required],
        cars: [[], Validators.required] 
      });
    }

        // Configuración del formulario dinámico
  const formGroupConfig = this.formFields.reduce((config, field) => {
    config[field.name] = ['', field.validators];
    return config;
  }, {});

  this.createForm = this.fb.group(formGroupConfig);
  }
  
    // Verificar si un campo es inválido
    isFieldInvalid(fieldName: string): boolean {
      const field = this.createForm.get(fieldName);
      return !!(field?.invalid && field?.touched);
  }


  onSubmit(){
    if (this.createForm.valid) {

      const formData = this.createForm.value;

     //console.log(formData);


    if (this.entity === 'Cars') {
      this.carService.addCar(formData).subscribe();   
    } else if (this.entity === 'Users') {
      this.userService.addUser(formData).subscribe();      
    } else if (this.entity === 'Orders') {
      this.orderService.addOrder(formData).subscribe();      
    } else if (this.entity === 'Reviews') {

      //formData.cars = formData.cars.map((car: Car) => car.car_Id);

      //formData.users = formData.users.map((user: User) => user.user_Id);

      console.log(formData);

      this.reviewService.addReview(formData).subscribe();      
    } else if (this.entity === 'Carts') {
      console.log("Coches seleccionados:", formData.cars);

      this.cartService.addCart(formData).subscribe();      
    }

    
    this.formSubmit.emit(formData); // Emitimos los datos del formulario
    this.createForm.reset(); // Limpiamos el formulario después de enviar
  }

  }


  onClose(): void {
    this.closeForm.emit(); // Emitimos un evento para cerrar el formulario
  }

    // Método para cargar los coches desde el servicio
  loadOptionsData() {

    forkJoin({
      cars: this.carService.getCars(),
      categories: this.carService.getCategories(),
      users: this.userService.getUsers(),
      roles: this.userService.getRoles(),
      reviews: this.reviewService.getReviews(),
      orders: this.orderService.getOrders(),
      statuses: this.orderService.getStatuses(),
      carts: this.cartService.getCarts()
    }).subscribe(results => {
      // Asigna los datos de cada resultado a las propiedades correspondientes
      this.cars = results.cars;
      this.categories = results.categories;
      this.users = results.users;
      this.roles = results.roles;
      this.reviews = results.reviews;
      this.orders = results.orders;
      this.orderStatuses = results.statuses;
      this.carts = results.carts;
    
      // Llama a buildForm solo después de que todos los datos se hayan cargado
      this.buildForm();
    }, error => {
      console.error('Error al cargar datos:', error);
    });

    setTimeout(() => {
      this.toppingList = ['Cheese', 'Bacon', 'Olives'];
      
      // Forzar la detección de cambios después de actualizar los datos
      this.cdRef.detectChanges();
    }, 2000);
  }

}