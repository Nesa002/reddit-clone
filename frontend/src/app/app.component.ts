import { Component } from '@angular/core';
import { HeaderComponent } from './shared/header/header.component';
import { ModalComponent } from './auth/modal/modal.component';
import { ToastComponent } from './shared/toast/toast/toast.component';

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  imports: [HeaderComponent, ModalComponent, ToastComponent],
})
export class AppComponent {}
