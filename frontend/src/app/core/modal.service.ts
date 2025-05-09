import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class ModalService {
  private isOpenSubject = new BehaviorSubject<boolean>(false);
  isOpen$ = this.isOpenSubject.asObservable()

  private isLogin = new BehaviorSubject<boolean>(true);
  isLogin$ = this.isLogin.asObservable()

  constructor(private authService: AuthService) {
    this.checkJwt();
  }

  private checkJwt(): void {
    if (!this.authService.token) {
      console.log('No valid JWT found. Opening modal.');
      this.isOpenSubject.next(true);
    } else {
      console.log('Valid JWT found. Modal remains closed.');
    }
  }

  open() {
    this.isOpenSubject.next(true);
  }

  close() {
    this.isOpenSubject.next(false);
  }

  toggleAuthState(): void {
    this.isLogin.next(!this.isLogin.value);
  }
  
}
