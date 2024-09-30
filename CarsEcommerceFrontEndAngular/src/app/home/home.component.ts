import { Component, ElementRef, HostListener, Renderer2, ViewChild } from '@angular/core';
import Swiper from 'swiper';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  title = 'CarsEcommerce';

  @ViewChild('menuBtn') menuBtn!: ElementRef;
  @ViewChild('navbar') navbar!: ElementRef;
  @ViewChild('loginFormContainer') loginFormContainer!: ElementRef;
  @ViewChild('signupFormContainer') singupFormContainer!: ElementRef;
  @ViewChild('header') header!: ElementRef;
  @ViewChild('home') home!: ElementRef;
  constructor(private renderer: Renderer2) {}

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
}
