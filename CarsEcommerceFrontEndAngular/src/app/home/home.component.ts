import { Component, ElementRef, HostListener, OnInit, Renderer2, ViewChild } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import Swiper from 'swiper';
import { User } from '../services/user/user';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../services/user/user.service';
import { AuthenticationService } from '../services/authentication/authentication.service';
import { Role } from '../services/role/role';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit{
  title = 'CarsEcommerce';

  isUserLoggedIn: boolean = false;

  
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



  constructor(private renderer: Renderer2,private router: Router,private route: ActivatedRoute,private fb: UntypedFormBuilder,private userService: UserService, public loginService: AuthenticationService) {
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

    this.loginService.isLoggedIn.subscribe((loggedIn) => {
      this.isUserLoggedIn = loggedIn;
      console.log("OnInit isUserLoggedIn: "+this.isUserLoggedIn);
    });
  }

  SignOut() {
    this.loginService.logOut();
    this.isUserLoggedIn = false;
  }

  ngAfterViewInit(): void {
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

  SignIn(){
    this.usernameEmpty = false;
    this.passwordEmpty = false;
    this.passwordKnown = false;
    this.KnownUser = false;

    if(this.signinForm.controls['username'].value == null || this.signinForm.controls['username'].value == ""){
      this.usernameEmpty = true;

    }

    if(this.signinForm.controls['password'].value == null || this.signinForm.controls['password'].value == ""){
      this.passwordEmpty = true;

    }

    if(this.usernameEmpty == false && this.passwordEmpty == false){

      // Llamar al servicio que realiza la solicitud al backend
      this.userService.loginUser(this.user).subscribe({
        next: (response) => {
          console.log('LogIn Successful:', response);
          // Cerrar el formulario o redirigir
          this.loginFormContainer.nativeElement.classList.remove('active');
          this.userService.getUserByUsername(this.user.username).subscribe(
            (user: User) => {  
              // Comprobar el rol del usuario
              console.log("User role:", user.role);
console.log("Role.ADMIN value:", Role.ADMIN);
              if (user.role === Role.ADMIN) {
                this.router.navigate(['/', 'admin']);
              console.log("Soy admin");
              }
            },
            (error) => {
              console.error('Error fetching user details:', error);
              // Manejo de errores si no se puede obtener el usuario
            }
          );          

          this.passwordKnown = true;
          this.isUserLoggedIn=true;
        },
        error: (error) => {
          if (error.status === 401) {
            console.log('Invalid password');
            this.passwordKnown = false;
          } else if (error.status === 404) {
            console.log('User not found');
            this.KnownUser = false;
          } else {
            console.log('An error occurred:', error);
          }
          }
        });
      /*for (let user of this.users) {
        if(user.username == this.signinForm.controls['username'].value){
          console.log(user);
              this.KnownUser = true;

            if(user.password == this.signinForm.controls['password'].value){
              console.log("LogIn Successful");
              this.loginFormContainer.nativeElement.classList.remove('active');        
              this.passwordKnown = true;
            }
        }
      }*/
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

}
