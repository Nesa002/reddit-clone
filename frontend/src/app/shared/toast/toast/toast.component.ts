import { Component } from '@angular/core';
import { ToastService } from '../toast.service';
import { AsyncPipe, NgIf } from '@angular/common';

@Component({
  selector: 'app-toast',
  standalone: true,
  imports: [NgIf, AsyncPipe],
  templateUrl: './toast.component.html',
  styleUrls: ['./toast.component.css'],
})
export class ToastComponent {
  constructor(public toastService: ToastService) {}
}
