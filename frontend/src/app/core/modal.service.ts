import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ModalService {
  private isOpenSubject = new BehaviorSubject<boolean>(true);
  isOpen$ = this.isOpenSubject.asObservable()

  private isLogin = new BehaviorSubject<boolean>(true);
  isLogin$ = this.isLogin.asObservable()

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
