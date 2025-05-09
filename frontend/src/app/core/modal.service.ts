import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ModalService {
  private isOpenSubject = new BehaviorSubject<boolean>(false);
  isOpen$ = this.isOpenSubject.asObservable()

  private isLogin = new BehaviorSubject<boolean>(true);
  isLogin$ = this.isLogin.asObservable()

  constructor() {
    const jwtToken = localStorage.getItem('jwt');
    if (!jwtToken) {
      this.isOpenSubject.next(true);
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
