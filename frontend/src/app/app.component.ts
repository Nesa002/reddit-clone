import { Component } from '@angular/core';
import { HeaderComponent } from './shared/header/header.component';
import { ModalComponent } from './auth/modal/modal.component';
import { ToastComponent } from './shared/toast/toast/toast.component';
import { SidebarComponent } from './shared/sidebar/sidebar.component';

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  imports: [HeaderComponent, ModalComponent, ToastComponent, SidebarComponent],
})
export class AppComponent {}
