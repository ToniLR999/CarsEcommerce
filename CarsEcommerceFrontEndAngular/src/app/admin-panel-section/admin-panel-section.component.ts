  import { Component, ElementRef, EventEmitter, Input, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
  import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
  import { User } from '../services/user/user';
  import { UserService } from '../services/user/user.service';
  import { ReviewService } from '../services/review/review.service';
  import { CarService } from '../services/car/car.service';
  import { CartService } from '../services/cart/cart.service';
  import { OrderService } from '../services/order/order.service';
  import { Car } from '../services/car/car';

  @Component({
    selector: 'app-admin-panel-section',
    templateUrl: './admin-panel-section.component.html',
    styleUrl: './admin-panel-section.component.css'
  })
  export class AdminPanelSectionComponent implements OnInit{
    @Input() entity: string = '';
    @Output() formSubmit = new EventEmitter<any>();
    @Output() closeForm = new EventEmitter<void>();

    createForm!: UntypedFormGroup;
    formFields: any[] = []; // Dinámico: lista de campos


    carBrands: string[] = ['Toyota', 'Honda', 'Ford', 'BMW', 'Audi', 'Mercedes'];


    constructor(private fb: UntypedFormBuilder, private userService: UserService, private carService: CarService, private reviewService: ReviewService, private cartService: CartService, private orderService: OrderService) {
    }


    ngOnInit() {
      this.buildForm(); // Construimos el formulario la primera vez
  

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
          { name: 'stock', type: 'text', placeholder: 'Stock', errors: [] }
        ];

              // Inicializar el formulario reactivo con controles
      this.createForm = this.fb.group({
        name: ['', Validators.required],
        description: ['', Validators.required],
        price: ['', Validators.required],
        stock: ['', Validators.required]
      });
      } else if (this.entity === 'Users') {

        this.formFields = [
          { name: 'username', type: 'text', placeholder: 'Username', errors: [] },
          { name: 'email', type: 'email', placeholder: 'Email', errors: [] },
          { name: 'phoneNumber', type: 'text', placeholder: 'PhoneNumber', errors: [] },
          { name: 'password', type: 'text', placeholder: 'Password', errors: []  },
          { name: 'role', type: 'select', placeholder: 'Role', errors: [], options: ['ADMIN','USER']  }
        ];

        this.createForm = this.fb.group({
          username: ['', Validators.required],        // Nombre de usuario
          email: ['', [Validators.required, Validators.email]],  // Correo electrónico
          phoneNumber: ['', [Validators.pattern('[0-9]{3}-[0-9]{3}-[0-9]{4}')]], // Número de teléfono
          password: ['', Validators.required],
          role: ['', Validators.required]
        
        
        });
      }
        else if (this.entity === 'Orders') {

          this.formFields = [
            { name: 'totalPrice', type: 'text', placeholder: 'Total Price', errors: [] },
            { name: 'status', type: 'select', placeholder: 'Select Status', errors: [],   options: ['Pending', 'Completed'] },
            { name: 'cars', type: 'select', placeholder: 'Select Car', errors: [], options: this.carBrands },
            { name: 'user', type: 'select', placeholder: 'Select User', errors: [],options: ['User1', 'User2', 'User3']  }
          ];
          this.createForm = this.fb.group({
            totalPrice: ['', Validators.required],        // Nombre de usuario
            status: ['', [Validators.required]],  // Correo electrónico
            cars: ['', [Validators.required, Validators.pattern('[0-9]{3}-[0-9]{3}-[0-9]{4}')]], // Número de teléfono
            user: ['', Validators.required]     // Contraseña
          });
      }
      else if (this.entity === 'Reviews') {
        
        this.formFields = [
          { name: 'rating', type: 'text', placeholder: 'Rating', errors: [] },
          { name: 'comment', type: 'text', placeholder: 'Comment', errors: [] },
          { name: 'user', type: 'select', placeholder: 'Select an User', errors: [],options: ['User1', 'User2', 'User3']  },
          { name: 'cars', type: 'select', placeholder: 'Select Cars', errors: [], options: this.carBrands  }
        ];
        this.createForm = this.fb.group({
          rating: ['', Validators.required],        // Nombre de usuario
          comment: ['', [Validators.required, Validators.email]],  // Correo electrónico
          user: ['', [Validators.required, Validators.pattern('[0-9]{3}-[0-9]{3}-[0-9]{4}')]], // Número de teléfono
          cars: ['', Validators.required]        // Contraseña
        });
      }
      else if (this.entity === 'Carts') {

        
        this.formFields = [
          { name: 'user', type: 'select', placeholder: 'Select an User', errors: [],options: ['User1', 'User2', 'User3'] },  
          { name: 'cars', type: 'select', placeholder: 'Select Cars', errors: [], options: this.carBrands  }
        ];
        this.createForm = this.fb.group({
          user: ['', Validators.required],        // Nombre de usuario
          cars: ['', Validators.required] // Confirmación de contraseña
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

        console.log(formData);


      if (this.entity === 'Cars') {
        this.carService.addCar(formData).subscribe();   
      } else if (this.entity === 'Users') {
        this.userService.addUser(formData).subscribe();      
      } else if (this.entity === 'Orders') {
        this.orderService.addOrder(formData).subscribe();      
      } else if (this.entity === 'Reviews') {
        this.reviewService.addReview(formData).subscribe();      
      } else if (this.entity === 'Carts') {
        this.cartService.addCart(formData).subscribe();      
      }

      
      this.formSubmit.emit(this.createForm.value); // Emitimos los datos del formulario
      this.createForm.reset(); // Limpiamos el formulario después de enviar
    }

    }


    onClose(): void {
      this.closeForm.emit(); // Emitimos un evento para cerrar el formulario
    }
    
  }
