import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { LoginRequestDTO } from './dto/login-request.dto';
import { RegisterRequestDTO } from './dto/register-request.dto';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = environment.server_path+"/api/users";

  constructor(private httpClient: HttpClient) { }

  login(LoginRequestDTO: LoginRequestDTO) {
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    
    return this.httpClient.post<{ token: string }>(this.apiUrl + "/login", LoginRequestDTO, {headers}).pipe();
  }

  register(RegisterRequestDTO: RegisterRequestDTO) {
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    
    return this.httpClient.post(this.apiUrl + "/register", RegisterRequestDTO, {headers}).pipe();
  }

  logout() {
    localStorage.removeItem('jwt');
  }

  get token(): string | null {
    const token = localStorage.getItem('jwt');
    if (token && this.isTokenExpired(token)) {
      console.log('Removing expired JWT');
      localStorage.removeItem('jwt');
      return null;
    }
    return token;
  }

  get currentUserRole() {
    const token = localStorage.getItem('jwt');
    if (!token) {
      return "NONE";
    }
    const payload = JSON.parse(atob(token.split('.')[1]));
    return payload.role;
  }

  currentUserId() {
    const token = localStorage.getItem('jwt');
    if (!token) {
      return null;
    }
    const payload = JSON.parse(atob(token.split('.')[1]));
    return payload.id;
  }

  get currentUserUsername() {
    const token = localStorage.getItem('jwt');
    if (!token) {
      return null;
    }
    const payload = JSON.parse(atob(token.split('.')[1]));
    return payload.sub;
  }

  isTokenExpired(token: string): boolean {
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      const expirationTime = payload.exp * 1000;
      return Date.now() >= expirationTime;
    } catch (e) {
      return true;
    }
  }
}
