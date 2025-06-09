import { Component, ElementRef, HostListener, OnInit, Renderer2, ViewChild } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import Swiper from 'swiper';
import { User } from '../services/user/user';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../services/user/user.service';
import { AuthenticationService } from '../services/authentication/authentication.service';
import { Role } from '../services/role/role';
import { CarService } from '../services/car/car.service';
import { Car } from '../services/car/car';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit{
  title = 'CarsEcommerce';

  isUserLoggedIn: boolean = false;

  sessionStorage = sessionStorage;
  
  @ViewChild('menuBtn') menuBtn!: ElementRef;
  @ViewChild('navbar') navbar!: ElementRef;
  @ViewChild('loginFormContainer') loginFormContainer!: ElementRef;
  @ViewChild('signupFormContainer') singupFormContainer!: ElementRef;
  @ViewChild('header') header!: ElementRef;
  @ViewChild('home') home!: ElementRef;

  users: User[] = [];
  user: User;
  signinForm: UntypedFormGroup;
  passwordNotMatch: boolean = false;
  unknownUser: boolean = false;
  invalidLogin = false;
  passwordKnown: boolean = true;
  KnownUser: boolean = true;


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


  data = {
    username: "",
    email: "",
    password: "",

  }

  cars: Car[] = [];
  loading: boolean = true;
  error: string | null = null;

  private readonly API_URL = 'http://localhost:8081';

  constructor(private renderer: Renderer2,private router: Router,private route: ActivatedRoute,private fb: UntypedFormBuilder,private userService: UserService, public loginService: AuthenticationService, public carService: CarService) {
    this.user = new User();

    this.signinForm= this.fb.group({
      username: this.fb.control(this.user.username,[Validators.required]),
      password: this.fb.control(this.user.password,[Validators.required])
    })
    this.userService.getUsers().subscribe(data => {
      this.users = data;
    });

    this.signUpForm = this.fb.group({
      username: this.fb.control(this.user.username,[Validators.required]),
      email: this.fb.control(this.user.email,[Validators.required,Validators.email]),
      phoneNumber: this.fb.control(this.user.phoneNumber,[Validators.required,Validators.pattern('[0-9]{3}-[0-9]{3}-[0-9]{4}')]),
      password: this.fb.control(this.user.password,[Validators.required]),
      confirmPassword: this.fb.control(null,[Validators.required])
    })
  }

  ngOnInit(): void {
    this.user = new User();

    this.signinForm= this.fb.group({
      username: this.fb.control(this.user.username,[Validators.required]),
      password: this.fb.control(this.user.password,[Validators.required])
    })

    this.userService.getUsers().subscribe(data => {
      this.users = data;
    });

    this.signUpForm= this.fb.group({
      username: this.fb.control(this.user.username,[Validators.required]),
      email: this.fb.control(this.user.email,[Validators.required,Validators.email]),
      phoneNumber: this.fb.control(this.user.phoneNumber,[Validators.required,Validators.pattern('[0-9]{3}-[0-9]{3}-[0-9]{4}')]),
      password: this.fb.control(this.user.password,[Validators.required]),
      confirmPassword: this.fb.control(null,[Validators.required])
    });

    const storedUsername = sessionStorage.getItem('username');
    if (storedUsername) {
      console.log("Usuario logueado:", storedUsername);
      this.isUserLoggedIn = true;  // O lo que necesites para cambiar el estado de tu app
    } else {
      console.log("No hay sesión activa");
      this.isUserLoggedIn = false;
    }

    this.loginService.isLoggedIn.subscribe((loggedIn) => {
      this.isUserLoggedIn = loggedIn;
      console.log("OnInit isUserLoggedIn: "+this.isUserLoggedIn);
    });

    this.loadCars();

    console.log(this.cars); 
  }

  SignOut() {
    // Limpiar el estado de login
    this.isUserLoggedIn = false;
    this.loginService.isLoggedIn.next(false);
    
    // Limpiar todo el sessionStorage
    sessionStorage.clear();
    
    // Limpiar el localStorage si existe
    localStorage.clear();
    
    // Opcional: Redirigir a la página principal
    this.router.navigate(['/']);
  }

  scrollToSection(section: string) {
    this.router.navigate([], { queryParams: { section } });
  }

  ngAfterViewInit(): void {
    this.route.queryParams.subscribe((params) => {
      const section = params['section'];
      if (section) {
        const element = document.getElementById(section);
        if (element) element.scrollIntoView({ behavior: 'smooth' });
      }
    });
    // Menu button toggle
    this.renderer.listen(this.menuBtn.nativeElement, 'click', () => {
      this.menuBtn.nativeElement.classList.toggle('fa-times');
      this.navbar.nativeElement.classList.toggle('active');
    });

    // Login form toggle
    const loginBtn = document.querySelector('#login-btn');
    if (loginBtn) {
      this.renderer.listen(loginBtn, 'click', () => {
        this.loginFormContainer.nativeElement.classList.toggle('active');
      });
    }
    const closeLoginForm = document.querySelector('#close-login-form');
    if (closeLoginForm) {
      this.renderer.listen(closeLoginForm, 'click', () => {
        this.loginFormContainer.nativeElement.classList.remove('active');
      });
    }


    // SingUp form toggle
    const SignUpBtn = document.getElementById('CreateOne-btn');
    if (SignUpBtn) {
      this.renderer.listen(SignUpBtn, 'click', () => {
        this.singupFormContainer.nativeElement.classList.toggle('active');
        this.loginFormContainer.nativeElement.classList.remove('active');
      });
    }

    const alreadyMemberBtn = document.querySelector('#AlreadyMember-btn');
    if (alreadyMemberBtn) {
      this.renderer.listen(alreadyMemberBtn, 'click', () => {
        this.loginFormContainer.nativeElement.classList.toggle('active');
        this.singupFormContainer.nativeElement.classList.remove('active');
      });
    }
    
    const closeSignUpForm = document.querySelector('#close-signup-form');
    if (closeSignUpForm) {
      this.renderer.listen(closeSignUpForm, 'click', () => {
        this.singupFormContainer.nativeElement.classList.remove('active');
      });
    }

    // Swiper initialization
    this.initSwiper();
  }

  SignIn() {
    this.usernameEmpty = false;
    this.passwordEmpty = false;
    this.passwordKnown = false;
    this.KnownUser = false;

    if(this.signinForm.controls['username'].value == null || this.signinForm.controls['username'].value == "") {
        this.usernameEmpty = true;
    }

    if(this.signinForm.controls['password'].value == null || this.signinForm.controls['password'].value == "") {
        this.passwordEmpty = true;
    }

    if(this.usernameEmpty == false && this.passwordEmpty == false) {
        const loginData = {
            username: this.signinForm.controls['username'].value,
            password: this.signinForm.controls['password'].value
        };

        this.userService.loginUser(loginData).subscribe({
            next: (response) => {
                console.log('LogIn Successful:', response);
                sessionStorage.setItem('username', loginData.username);
                
                // Cerrar el popup de login
                this.loginFormContainer.nativeElement.classList.remove('active');
                
                // Actualizar el estado de login
                this.isUserLoggedIn = true;
                this.loginService.isLoggedIn.next(true);
                
                // Limpiar el formulario
                this.signinForm.reset();
            },
            error: (error) => {
                console.error('Error en login:', error);
                if (error.status === 401) {
                    this.passwordKnown = false;
                } else if (error.status === 404) {
                    this.KnownUser = false;
                }
            }
        });
    }
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
          this.singupFormContainer.nativeElement.classList.remove('active');
          this.loginFormContainer.nativeElement.classList.toggle('active');        
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

  // Handle window scroll
  @HostListener('window:scroll', [])
  onWindowScroll() {
    if (window.scrollY > 0) {
      this.header.nativeElement.classList.add('active');
    } else {
      this.header.nativeElement.classList.remove('active');
    }

    this.menuBtn.nativeElement.classList.remove('fa-times');
    this.navbar.nativeElement.classList.remove('active');
  }

  // Handle parallax effect in the home section
  onHomeMouseMove(e: MouseEvent) {
    const parallaxElements = this.home.nativeElement.querySelectorAll('.home-parallax');
    parallaxElements.forEach((elm: any) => {
      const speed = elm.getAttribute('data-speed');
      const x = (window.innerWidth - e.pageX * speed) / 90;
      const y = (window.innerHeight - e.pageY * speed) / 90;
      elm.style.transform = `translateX(${y}px) translateY(${x}px)`;
    });
  }

  onHomeMouseLeave() {
    const parallaxElements = this.home.nativeElement.querySelectorAll('.home-parallax');
    parallaxElements.forEach((elm: any) => {
      elm.style.transform = 'translateX(0px) translateY(0px)';
    });
  }

  // Initialize Swiper sliders
  initSwiper() {
    new Swiper('.vehicles-slider', {
      grabCursor: true,
      centeredSlides: true,
      spaceBetween: 20,
      loop: true,
      autoplay: {
        delay: 9500,
        disableOnInteraction: false,
      },
      pagination: {
        el: '.swiper-pagination',
        clickable: true,
      },
      breakpoints: {
        0: { slidesPerView: 1 },
        768: { slidesPerView: 2 },
        1024: { slidesPerView: 3 },
      },
    });

    new Swiper('.featured-slider', {
      grabCursor: true,
      centeredSlides: true,
      spaceBetween: 20,
      loop: true,
      autoplay: {
        delay: 9500,
        disableOnInteraction: false,
      },
      pagination: {
        el: '.swiper-pagination',
        clickable: true,
      },
      breakpoints: {
        0: { slidesPerView: 1 },
        768: { slidesPerView: 2 },
        1024: { slidesPerView: 3 },
      },
    });

    new Swiper('.review-slider', {
      grabCursor: true,
      centeredSlides: true,
      spaceBetween: 20,
      loop: true,
      autoplay: {
        delay: 9500,
        disableOnInteraction: false,
      },
      pagination: {
        el: '.swiper-pagination',
        clickable: true,
      },
      breakpoints: {
        0: { slidesPerView: 1 },
        768: { slidesPerView: 2 },
        1024: { slidesPerView: 3 },
      },
    });
  }

  
  getEmail(){
    console.log(this.signUpForm.controls['email'].value);
    return this.signUpForm.controls['email'].value;

  }

  getUsername(): string {
    return sessionStorage.getItem('username') || '';
  }

  loadCars() {
    this.carService.getCars().subscribe({
        next: (data) => {
            this.cars = data;
            this.loading = false;
        },
        error: (error) => {
            this.error = 'Error al cargar los coches';
            this.loading = false;
            console.error('Error:', error);
        }
    });
  }

  viewCarDetails(carId: number) {
    this.router.navigate(['/car', carId]);
  }

  formatImageUrl(imageUrl: string | undefined): string {
    if (!imageUrl) return '';
    
    // Si la URL ya es completa, la devolvemos tal cual
    if (imageUrl.startsWith('http')) {
        return imageUrl;
    }
    
    // Si es una URL relativa, la construimos con la URL base
    // Eliminamos 'assets/img/' de la ruta si existe
    const cleanPath = imageUrl.replace('assets/img/', '');
    return `${this.API_URL}/car-images/${cleanPath}`;
  }

  handleImageError(event: any) {
    const imgElement = event.target;
    const parentElement = imgElement.parentElement;
    
    // Ocultar la imagen
    imgElement.style.display = 'none';
    
    // Buscar o crear el div de fallback
    let fallbackDiv = parentElement.querySelector('.no-image');
    if (!fallbackDiv) {
        fallbackDiv = document.createElement('div');
        fallbackDiv.className = 'no-image';
        fallbackDiv.innerHTML = '<i class="fas fa-car"></i>';
        parentElement.appendChild(fallbackDiv);
    }
    
    // Mostrar el div de fallback
    fallbackDiv.style.display = 'flex';
    
    // Log para debugging
    console.log('Error al cargar la imagen:', imgElement.src);
  }

  needsSmallerImage(car: Car): boolean {
    const largeImageModels = [
        'Formentor VZ5',
        'Macan GTS',
        'Taycan',
        'Model 3',
        'Model S',
        'Model Y',
        'Model X',
        'RS3'
    ];
    return car.modelo ? largeImageModels.includes(car.modelo) : false;
  }

}
