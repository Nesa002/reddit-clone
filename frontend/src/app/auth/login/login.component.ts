import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ModalService } from '../../core/modal.service';
import { AsyncPipe, NgIf } from '@angular/common';
import {LoginRequestDTO} from '../dto/login-request.dto';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [NgIf, AsyncPipe, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginForm: FormGroup; 
  loginErrorMessage: string | null = null;

  constructor(private fb: FormBuilder, public modalService: ModalService, public authService: AuthService) {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
    })
  }

  close() {
    this.modalService.close();
  }

  onFocus(controlName: string) {
    this.loginErrorMessage = null;
    this.loginForm.get(controlName)?.markAsUntouched();
  }

  onSubmit() {
    if (!this.loginForm.valid) {
      return;
    }

    const loginData: LoginRequestDTO = this.loginForm.value;
    
    this.authService.login(loginData).subscribe({
      next: (response) => {
        localStorage.setItem('jwt', response.token);
        this.close()
      },
      error: (error) => {
        console.error('Login failed:', error);
        this.loginErrorMessage = "Invalid username or password.";
      }
    });

  }


}
