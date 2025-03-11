import { ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output, SimpleChanges } from '@angular/core';
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


@Component({
  selector: 'app-admin-panel-section',
  templateUrl: './admin-panel-section.component.html',
  styleUrl: './admin-panel-section.component.css'
})
export class AdminPanelSectionComponent implements OnInit{
  @Input() entity: string = '';
  @Input() itemToEdit: any = null;
  @Output() formSubmit = new EventEmitter<any>();
  @Output() closeForm = new EventEmitter<void>();

  isEditing: boolean = false;  // Indica si estamos editando
  
  createForm!: UntypedFormGroup;
  formFields: any[] = []; // Dinámico: lista de campos

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

    console.log("Editando item: ", this.itemToEdit);

    if (changes['entity'] || changes['itemToEdit']) {
      this.isEditing = !!this.itemToEdit;
      this.buildForm(); // Volvemos a construir el formulario si cambia la entidad
      setTimeout(() => this.cdRef.detectChanges(), 0); // Asegura la actualización

    }
  }

  buildForm() {

    // Valores por defecto: si estamos editando, usamos itemToEdit; sino, valores vacíos

    const defaultValues: any = this.itemToEdit ? this.itemToEdit : {};

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

    this.createForm = this.fb.group({
      name: [defaultValues.name || '', Validators.required],
      description: [defaultValues.description || '', Validators.required],
      price: [defaultValues.price || '', Validators.required],
      stock: [defaultValues.stock || '', Validators.required],
      category: [defaultValues?.category || '', Validators.required],
      images: [defaultValues.images || []],
      orders: [defaultValues.orders ? defaultValues.orders.map((c: Order) => c.id) : []],
      reviews: [defaultValues.reviews ? defaultValues.reviews.map((c: Review) => c.id) : []],
      carts: [defaultValues.carts ? defaultValues.carts.map((c: Cart) => c.id) : []]
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

      const defaults = defaultValues || {};
      console.log("defaults:", defaults);
      this.createForm = this.fb.group({
        username: [defaults.username || '', Validators.required],
        email: [defaults.email || '', [Validators.required, Validators.email]],
        phoneNumber: [defaults.phoneNumber || '', [Validators.pattern('[0-9]{10}')]],
        password: [defaults.password || '', Validators.required],
        role: [defaults?.role || '', Validators.required],
        orders: [defaults.orderIds ? defaultValues.orderIds.map((c: Order) => c.id) :  []],
        reviews: [defaults.reviewIds ? defaultValues.reviewIds.map((c: Review) => c.id) : []],
        cart: [defaults?.cartId || null]
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
          totalPrice: [defaultValues.totalPrice || null],
          status: [defaultValues?.status || '', [Validators.required]],
          cars: [defaultValues.cars ? defaultValues.cars.map((c: Car) => c.id) :  [], [Validators.required]],
          user: [defaultValues.user ? defaultValues.user.id : '', Validators.required]
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
        rating: [defaultValues.rating || '', Validators.required],
        comment: [defaultValues.comment || '', Validators.required],
        user: [defaultValues.user ? defaultValues.user.id : '', Validators.required],
        car: [defaultValues.car ? defaultValues.car.id :  '', Validators.required]
      });
    }
    else if (this.entity === 'Carts') {

      
      this.formFields = [
        { name: 'user', type: 'select', placeholder: 'Select an User', errors: [],options: this.users },  
        { name: 'cars', type: 'select-multiple', placeholder: 'Select Cars', errors: [], options: this.cars }
      ];

      this.createForm = this.fb.group({
        user: [defaultValues.user  ? defaultValues.user.id : '', Validators.required],
        cars: [defaultValues.cars ? defaultValues.cars.map((c: Car) => c.id) :  [], Validators.required]
      });
    }

  }
  
    // Verificar si un campo es inválido
    isFieldInvalid(fieldName: string): boolean {
      const field = this.createForm.get(fieldName);
      return !!(field?.invalid && field?.touched);
  }

  onSubmit(){
    console.log("Submited");

    console.log("¿Formulario válido?", this.createForm.valid);
    console.log("Valor del formulario:", this.createForm.value);

    console.log("Estado del formulario:", this.createForm.status);

    Object.keys(this.createForm.controls).forEach((key) => {
      const control = this.createForm.get(key);
      console.log(`Campo: ${key}, Estado: ${control?.status}, Errores:`, control?.errors);
    });
    


    console.log("Valores del formulario:", this.createForm.value);

    this.createForm.updateValueAndValidity();

    
    console.log('Validadores de orders:', this.createForm.get('orders')?.validator);
    console.log('Validadores de reviews:', this.createForm.get('reviews')?.validator);
    console.log('Validadores de cart:', this.createForm.get('cart')?.validator);

      if (this.createForm.valid) {

      const formData = { ...this.createForm.value };

      if (this.isEditing && this.itemToEdit) {
        formData.id = this.itemToEdit.id;  // Asegurar que el ID está presente para actualizar
      }

      console.log('Datos antes de enviar:', JSON.stringify(formData, null, 2));

      
      if (this.entity === 'Cars') {
        formData.images = Array.isArray(formData.images) ? formData.images.filter((image: any) => !!image) : [];
        formData.orders = Array.isArray(formData.orders) ? formData.orders.filter((order: any) => !!order) : [];
        formData.reviews = Array.isArray(formData.reviews) ? formData.reviews.filter((review: any) => !!review) : [];
        formData.carts = Array.isArray(formData.carts) ? formData.carts.filter((cart: any) => !!cart) : [];
      
        this.carService.addCar(formData).subscribe();   

      }
      
      else if (this.entity === 'Users') {

        console.log("entro en users");
        // Asegurarse de que 'orders' sea un arreglo no vacío
        formData.orders = Array.isArray(formData.orders) ? formData.orders.filter((order: any) => order) : [];
        formData.reviews = Array.isArray(formData.reviews) ? formData.reviews.filter((review: any) => review) : [];
        formData.cart = formData.cart && typeof formData.cart === 'object' && Object.keys(formData.cart).length > 0 ? formData.cart : null;

        this.userService.addUser(formData).subscribe();      

      }else if (this.entity === 'Orders') {
        // Asegurarse de que 'orders' sea un arreglo no vacío
        console.log("Procesando orders...");
        const formData = { ...this.createForm.value };

                // Asegurar que solo se envían los IDs
                formData.userId = formData.user?.id;
                formData.carIds = Array.isArray(formData.cars) ? formData.cars.map((car: any) => car.id) : [];
                
                // Eliminar los objetos completos del envío
                delete formData.user;
                delete formData.cars;
                
                console.log("Datos a enviar:", JSON.stringify(formData, null, 2));

        //formData.cars = Array.isArray(formData.cars) ? formData.cars.filter((car: any) => car) : [];

        this.orderService.addOrder(formData).subscribe();      

      }else if (this.entity === 'Reviews') {
        // Asegurarse de que 'orders' sea un arreglo no vacío
        /*formData.user = formData.user && typeof formData.user === 'object' && Object.keys(formData.user).length > 0 ? formData.user : null;
        formData.car = formData.car && typeof formData.car === 'object' && Object.keys(formData.car).length > 0 ? formData.car : null;*/

        console.log("Procesando reviews...");
        const formData = { ...this.createForm.value };

        // Asegurar que solo se envían los IDs
        formData.userId = formData.user?.id;
        formData.carId = formData.car?.id;
        
        // Eliminar los objetos completos del envío
        delete formData.user;
        delete formData.car;
        
        console.log("Datos a enviar:", JSON.stringify(formData, null, 2));
        

        if (!formData.userId || !formData.carId) {
          console.error("Error: ID de usuario o coche es null", formData);
          return; // Evita enviar la solicitud si falta un ID
      }
    
        console.log("Form a enviar:", formData);
        this.reviewService.addReview(formData).subscribe();

      }else if (this.entity === 'Carts') {
        // Asegurarse de que 'orders' sea un arreglo no vacío
        formData.user = formData.user && typeof formData.user === 'object' && Object.keys(formData.user).length > 0 ? formData.user : null;
        formData.cars = Array.isArray(formData.cars) ? formData.cars.filter((car: any) => car) : [];

        this.cartService.addCart(formData).subscribe();      
      }

      // Verificar el formato y los datos de 'orders' antes de hacer el envío
      console.log('Datos antes de enviar:', JSON.stringify(formData, null, 2));

      this.formSubmit.emit(formData); // Emitimos los datos del formulario
      this.createForm.reset(); // Limpiamos el formulario después de enviar
      this.isEditing = false;
      this.itemToEdit  = null;
  }else {
    console.warn("Formulario inválido.");
  }

  }


  onClose(): void {
    this.isEditing = false;
    this.itemToEdit  = null;
    this.createForm.reset();
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
    
      console.log("Datos cargados, ahora construyendo el formulario...");
      this.buildForm();
    }, error => {
      console.error('Error al cargar datos:', error);
    });

  }


  forzarValidacion() {
    Object.entries(this.createForm.controls).forEach(([controlName, control]) => {
      control.markAsTouched();
      control.updateValueAndValidity();
  
      if (control.invalid) {
        console.warn(`❌ Error en el control '${controlName}':`, control.errors);
      } else {
        console.log(`✅ El control '${controlName}' es válido.`);
      }
    });
  
    console.log('📋 Estado general del formulario:', this.createForm.status);
  }  

}


