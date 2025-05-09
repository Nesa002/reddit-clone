import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ModalService } from '../../../core/modal.service';
import { AuthService } from '../../auth.service';
import { NgIf } from '@angular/common';
import { ToastService } from '../../../shared/toast/toast.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [NgIf, ReactiveFormsModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registerForm: FormGroup;
  registerErrorMessage: string | null = null;

  constructor(private fb: FormBuilder, public modalService: ModalService, public authService: AuthService, private toastService: ToastService) {
    this.registerForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(30)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      bio: ['']
    });
  }

  close() {
    this.modalService.close();
  }

  onFocus(controlName: string) {
    this.registerErrorMessage = null;
    this.registerForm.get(controlName)?.markAsUntouched();
  }

  onSubmit() {
    if (!this.registerForm.valid) {
      return;
    }

    const registerData = this.registerForm.value;

    this.authService.register(registerData).subscribe({
      next: (response) => {
        console.log('Registration successful:', response);
        this.toastService.showToast('Registration successful!', 2000);
        this.modalService.toggleAuthState();
      },
      error: (error) => {
        console.error('Registration failed:', error);
        this.registerErrorMessage = 'Registration failed. Please try again.';
      }
    });
  }
}
