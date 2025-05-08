import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { LoginRequestDTO } from './dto/login-request.dto';

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

  logout() {
    localStorage.removeItem('jwt');
  }

  get jwt() {
    return localStorage.getItem('jwt');
  }

  get currentUserRole() {
    const token = localStorage.getItem('jwt');
    if (!token) {
      return "NONE";
    }
    const payload = JSON.parse(atob(token.split('.')[1]));
    return payload.role;
  }

  get currentUserId() {
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
}
