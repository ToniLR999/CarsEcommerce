import { Component, ElementRef, EventEmitter, Input, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { User } from '../services/user/user';
import { UserService } from '../services/user/user.service';

@Component({
  selector: 'app-admin-panel-section',
  templateUrl: './admin-panel-section.component.html',
  styleUrl: './admin-panel-section.component.css'
})
export class AdminPanelSectionComponent implements OnInit{
  @Input() entity: string = '';
  @Output() formSubmit = new EventEmitter<any>();
  @Output() closeForm = new EventEmitter<void>();

  carBrands: string[] = ['Toyota', 'Honda', 'Ford', 'BMW', 'Audi', 'Mercedes'];


  @ViewChild('createCarFormContainer') addCarFormContainer!: ElementRef;
  createForm!: UntypedFormGroup;

  user: User;
  signUpForm!: UntypedFormGroup;
  usernameRepeated: boolean = false;
  usernameEmpty: boolean = false;
  emailEmpty: boolean = false;
  passwordsNotMatch: boolean = false;
  emailRepeated: boolean = false;
  emailInvalid: boolean = false;
  phoneInvalid: boolean = false;
  phoneRepeated: boolean = false;
  passwordEmpty: boolean = false;
  confirmPasswordEmpty: boolean = false;
  emailPattern =  new RegExp (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/);
  phonePattern = new RegExp(/^\d{3}-\d{3}-\d{4}$/);

  constructor(private fb: UntypedFormBuilder, private userService: UserService) {
    this.user = new User();

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
      this.createForm = this.fb.group({
        name: ['', Validators.required],          // Marca del coche
        description: ['', Validators.required],         // Modelo
        price: ['', [Validators.required, Validators.pattern('^[0-9]{4}$')]], // Año de fabricación
        stock: ['', Validators.required]          // Precio
      });
    } else if (this.entity === 'Users') {
      this.createForm = this.fb.group({
        username: ['', Validators.required],        // Nombre de usuario
        email: ['', [Validators.required, Validators.email]],  // Correo electrónico
        phoneNumber: ['', [Validators.required, Validators.pattern('[0-9]{3}-[0-9]{3}-[0-9]{4}')]], // Número de teléfono
        password: ['', Validators.required],        // Contraseña
        confirmPassword: ['', Validators.required]  // Confirmación de contraseña
      });
    }
    else if (this.entity === 'Orders') {
      this.createForm = this.fb.group({
        totalPrice: ['', Validators.required],        // Nombre de usuario
        status: ['', [Validators.required]],  // Correo electrónico
        cars: ['', [Validators.required, Validators.pattern('[0-9]{3}-[0-9]{3}-[0-9]{4}')]], // Número de teléfono
        user: ['', Validators.required]     // Contraseña
      });
    }
    else if (this.entity === 'Reviews') {
      this.createForm = this.fb.group({
        rating: ['', Validators.required],        // Nombre de usuario
        comment: ['', [Validators.required, Validators.email]],  // Correo electrónico
        user: ['', [Validators.required, Validators.pattern('[0-9]{3}-[0-9]{3}-[0-9]{4}')]], // Número de teléfono
        cars: ['', Validators.required]        // Contraseña
      });
    }
    else if (this.entity === 'Carts') {
      this.createForm = this.fb.group({
        user: ['', Validators.required],        // Nombre de usuario
        cars: ['', Validators.required] // Confirmación de contraseña
      });
    }
  }
  onSubmit(){
    if (this.createForm.valid) {

    if (this.entity === 'Cars') {
      // Lógica para crear un registro de Car
    } else if (this.entity === 'Users') {
      // Lógica para crear un registro de User
    }

    
    this.formSubmit.emit(this.createForm.value); // Emitimos los datos del formulario
    this.createForm.reset(); // Limpiamos el formulario después de enviar
  }

  }


  onClose(): void {
    this.closeForm.emit(); // Emitimos un evento para cerrar el formulario
  }
  

  SignUp(){
    this.usernameRepeated = false;
    this.usernameEmpty = false;
    this.emailEmpty = false;
    this.passwordEmpty = false;
    this.confirmPasswordEmpty = false;
    this.passwordsNotMatch = false;
    this.emailRepeated = false;
    this.emailInvalid = false;
    this.phoneInvalid = false;
    this.phoneRepeated = false;

    this.emailPattern =  new RegExp (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/);
    this.phonePattern = new RegExp(/^\d{3}-\d{3}-\d{4}$/);


    if(this.signUpForm.controls['username'].value == null || this.signUpForm.controls['username'].value == ""){
      this.usernameEmpty = true;
      
    }

    if(this.signUpForm.controls['email'].value == null || this.signUpForm.controls['email'].value == ""){
      this.emailEmpty = true;

    }

    if(this.signUpForm.controls['password'].value == null || this.signUpForm.controls['password'].value == ""){
      this.passwordEmpty = true;

    }
    if(this.signUpForm.controls['confirmPassword'].value == null || this.signUpForm.controls['confirmPassword'].value == ""){
      this.confirmPasswordEmpty = true;
    }
    if (!this.phonePattern.test(this.signUpForm.controls['phoneNumber'].value) && (this.signUpForm.controls['phoneNumber'].value != "" && this.signUpForm.controls['phoneNumber'].value != null)) {
      this.phoneInvalid = true;
      console.log("El número de teléfono no tiene el formato correcto");
    }

    if(this.usernameEmpty == false){
      this.userService.getUsers().subscribe(data => {
      
        data.forEach(user => {
          console.log(user);  
        if(this.signUpForm.controls['username'].value == user.username){
          console.log("User repeated "+this.signUpForm.controls['username'].value+"  "+user.username);
          this.usernameRepeated = true;
        }
        });

      })

    }

    if(this.emailEmpty == false){
      
    if(this.emailPattern.test(this.signUpForm.controls['email'].value)){
      console.log("cuadra con el patron "+this.signUpForm.controls['email'].value + "  : "+this.emailPattern);
      this.emailInvalid = false;
    }else{
      console.log("no cuadra con el patron "+this.signUpForm.controls['email'].value+ "  : "+this.emailPattern);
      this.emailInvalid = true;
    }

    if(this.emailInvalid == false){
      this.userService.getUsers().subscribe(data => {
      
        data.forEach(user => {
          console.log(user);  
        if(this.signUpForm.controls['email'].value == user.email){
          this.emailRepeated = true;
        }
        });

      })
    }
  }


      if(this.signUpForm.controls['password'].value == this.signUpForm.controls['confirmPassword'].value && this.signUpForm.controls['password'].value != null){

        this.passwordsNotMatch = false;
        this.userService.addUser(this.user).subscribe(data => {
          //this.singupFormContainer.nativeElement.classList.remove('active');
        })
      }else{
        if(this.signUpForm.controls['password'].value == null || this.signUpForm.controls['password'].value == ""){
          this.passwordEmpty = true;

        }
        if(this.signUpForm.controls['confirmPassword'].value == null || this.signUpForm.controls['confirmPassword'].value == ""){
          this.confirmPasswordEmpty = true;
        }

          this.passwordsNotMatch = true;

      }
    


  }

}
