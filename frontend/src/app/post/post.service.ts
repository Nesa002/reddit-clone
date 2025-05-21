import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FilterType } from './enum/post.filter.type';

@Injectable({
  providedIn: 'root'
})
export class PostService {
  private apiUrl = environment.server_path+"/api/posts";

  constructor(private httpClient: HttpClient) { }

  getPosts(userId: string, page: number, size: number, filterType: FilterType): Observable<any> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('filterType', filterType);

    return this.httpClient.get<any>(`${this.apiUrl}/feed/${userId}`, { params });
  }
}
