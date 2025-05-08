import { Component } from '@angular/core';
import { ModalService } from '../../core/modal.service';

@Component({
  selector: 'app-header',
  standalone: true,
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  constructor(private modalService: ModalService) {}

  openLogin() {
    this.modalService.open();
  }
}
