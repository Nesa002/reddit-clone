import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ModalService } from '../../core/modal.service';
import { AsyncPipe, NgIf } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [NgIf, AsyncPipe, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginForm: FormGroup; 

  constructor(private fb: FormBuilder, public modalService: ModalService) {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
    })
  }

  close() {
    this.modalService.close();
  }

  onSubmit() {
    if (this.loginForm.valid) {
      this.close();
    }
  }

}
