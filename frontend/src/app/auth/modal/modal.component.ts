import { Component } from '@angular/core';
import { ModalService } from '../../core/modal.service';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { AsyncPipe, NgIf } from '@angular/common';

@Component({
  selector: 'app-modal',
  standalone: true,
  imports: [LoginComponent, RegisterComponent, NgIf, AsyncPipe],
  templateUrl: './modal.component.html',
  styleUrl: './modal.component.css'
})
export class ModalComponent {
  isLogin: Boolean = true;

  constructor(public modalService: ModalService) {
    this.modalService.isLogin$.subscribe((isLogin) => {
      this.isLogin = isLogin;
    });
  }

  toggleAuth() {
    this.modalService.toggleAuthState();
  }
}
