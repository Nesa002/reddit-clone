import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PostService {
  private apiUrl = environment.server_path+"/api/posts";

  constructor(private httpClient: HttpClient) { }

  getPosts(userId: string, page: number, size: number): Observable<any> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())

    return this.httpClient.get<any>(`${this.apiUrl}/feed/${userId}`, { params });
  }
}
