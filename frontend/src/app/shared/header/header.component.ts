import { Component } from '@angular/core';
import { ModalService } from '../../core/modal.service';
import { AsyncPipe, NgIf } from '@angular/common';
import { AuthService } from '../../auth/auth.service';
import { ToastService } from '../toast/toast.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [NgIf, AsyncPipe],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
})
export class HeaderComponent {
  isDropdownOpen: boolean = false;

  constructor(
    private authService: AuthService,
    private modalService: ModalService
  ) {}

  ngOnInit() {
    document.addEventListener('click', this.handleOutsideClick.bind(this));
  }
  
  ngOnDestroy() {
    document.removeEventListener('click', this.handleOutsideClick.bind(this));
  }
  
  handleOutsideClick(event: MouseEvent) {
    const target = event.target as HTMLElement;
    if (!target.closest('.dropdown') && !target.closest('.profile-container')) {
      this.isDropdownOpen = false;
    }
  }

  logout() {
    this.authService.logout();
    this.modalService.open();
  }

  toggleDropdown() {
    this.isDropdownOpen = !this.isDropdownOpen;
  }
}
