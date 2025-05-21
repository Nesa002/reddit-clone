import { Component } from '@angular/core';
import { HeaderComponent } from './shared/header/header.component';
import { ModalComponent } from './auth/modal/modal.component';
import { ToastComponent } from './shared/toast/toast/toast.component';
import { SidebarComponent } from './shared/sidebar/sidebar.component';
import { PostListComponent } from './post/post-list/post-list.component';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  imports: [HeaderComponent, ModalComponent, ToastComponent, SidebarComponent, PostListComponent, RouterOutlet],
})
export class AppComponent {}
