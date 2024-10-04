import { Component, ElementRef, HostListener, OnInit, Renderer2, ViewChild } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import Swiper from 'swiper';
import { User } from '../services/user/user';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../services/user/user.service';
import { AuthenticationService } from '../services/authentication/authentication.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit{
  title = 'CarsEcommerce';


  
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

  userForm!: UntypedFormGroup;
  usernameRepeated: boolean = false;
  usernameEmpty: boolean = false;
  emailEmpty: boolean = false;
  passwordsNotMatch: boolean = false;
  emailRepeated: boolean = false;
  emailInvalid: boolean = false;
  passwordEmpty: boolean = false;
  confirmPasswordEmpty: boolean = false;
  emailPattern =  new RegExp (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/);

  data = {
    username: "",
    email: "",
    password: "",

  }



  constructor(private renderer: Renderer2,private router: Router,private route: ActivatedRoute,private fb: UntypedFormBuilder,private userService: UserService, private loginservice: AuthenticationService) {
    this.user = new User();

    this.signinForm= this.fb.group({
      username: this.fb.control(this.user.username,[]),
      password: this.fb.control(this.user.password,[])
    })
    this.userService.getUsers().subscribe(data => {
      this.users = data;
    });

    this.userForm = this.fb.group({
      username: this.fb.control(this.user.username,[]),
      email: this.fb.control(this.user.email,[]),
      password: this.fb.control(this.user.password,[]),
      confirmPassword: this.fb.control(null,[])
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

    this.userForm= this.fb.group({
      username: this.fb.control(this.user.username,[]),
      email: this.fb.control(this.user.email,[]),
      password: this.fb.control(this.user.password,[]),
      confirmPassword: this.fb.control(null,[])
    });
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
        console.log("He clickado");
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
    for (let user of this.users) {
    if (this.loginservice.authenticate(this.signinForm.controls['username'].value,
    this.signinForm.controls['password'].value,user)
    ) {
      this.router.navigate(['/','Home']);
      this.invalidLogin = false
    } else{
      this.invalidLogin = true
    }
    console.log(this.invalidLogin);
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


  SignUp(){
    this.usernameRepeated = false;
    this.usernameEmpty = false;
    this.emailEmpty = false;
    this.passwordEmpty = false;
    this.confirmPasswordEmpty = false;
    this.passwordsNotMatch = false;
    this.emailRepeated = false;
    this.emailInvalid = false;
    this.emailPattern =  new RegExp (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/);

    console.log(this.userForm.controls['username'].value);

    if(this.userForm.controls['username'].value == null || this.userForm.controls['username'].value == ""){
      this.usernameEmpty = true;
      
    }

    if(this.userForm.controls['email'].value == null || this.userForm.controls['email'].value == ""){
      this.emailEmpty = true;
  
    }

    if(this.usernameEmpty == false){
      this.userService.getUsers().subscribe(data => {
      
        data.forEach(user => {
          console.log(user);  
        if(this.userForm.controls['username'].value == user.username){
          console.log("User repeated "+this.userForm.controls['username'].value+"  "+user.username);
          this.usernameRepeated = true;
        }
        });
  
      })

    }

    if(this.emailEmpty == false){
      
    if(this.emailPattern.test(this.userForm.controls['email'].value)){
      console.log("cuadra con el patron "+this.userForm.controls['email'].value + "  : "+this.emailPattern);
      this.emailInvalid = false;
    }else{
      console.log("no cuadra con el patron "+this.userForm.controls['email'].value+ "  : "+this.emailPattern);
      this.emailInvalid = true;
    }

    if(this.emailInvalid == false){
      this.userService.getUsers().subscribe(data => {
      
        data.forEach(user => {
          console.log(user);  
        if(this.userForm.controls['email'].value == user.email){
          this.emailRepeated = true;
        }
        });
  
      })
    }
  }


      if(this.userForm.controls['password'].value == this.userForm.controls['confirmPassword'].value && this.userForm.controls['password'].value != null){

        this.passwordsNotMatch = false;
        this.userService.addUser(this.user).subscribe(data => {
          this.router.navigateByUrl('/LogIn');
        })
      }else{
        if(this.userForm.controls['password'].value == null || this.userForm.controls['password'].value == ""){
          this.passwordEmpty = true;
  
        }
        if(this.userForm.controls['confirmPassword'].value == null || this.userForm.controls['confirmPassword'].value == ""){
          this.confirmPasswordEmpty = true;
        }
  
          this.passwordsNotMatch = true;
  
      }
    


  }

  
  getEmail(){
    console.log(this.userForm.controls['email'].value);
    return this.userForm.controls['email'].value;

  }
}
