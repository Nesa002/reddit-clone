import { Component } from '@angular/core';
import { HeaderComponent } from './shared/header/header.component';
import { LoginComponent } from './auth/login/login.component';

@Component({
  selector: 'app-root',
  standalone: true,
  template: `
    <app-header></app-header>
    <app-login></app-login>
  `,
  imports: [HeaderComponent, LoginComponent],
})
export class AppComponent {}
