import { Component } from '@angular/core';
import { AuthService } from '../../auth/auth.service';
import { PostService } from '../post.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-post-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './post-list.component.html',
  styleUrl: './post-list.component.css'
})
export class PostListComponent {
  posts: any[] = [];
  page = 0;
  size = 10;

  constructor(private postService: PostService, private authService: AuthService) {}

  ngOnInit(): void {
    const userId = this.authService.currentUserId();
    if (userId) {
      this.loadPosts(userId);
    } else {
      console.error('User ID not found in token');
    }
  }

  loadPosts(userId: string): void {
    this.postService.getPosts(userId, this.page, this.size).subscribe(
      (response) => {
        this.posts = response.content;
        console.log('Posts loaded:', this.posts);
      },
      (error) => {
        console.error('Error loading posts:', error);
      }
    );
  }
}
