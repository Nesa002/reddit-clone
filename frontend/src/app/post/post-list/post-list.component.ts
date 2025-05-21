import { Component } from '@angular/core';
import { AuthService } from '../../auth/auth.service';
import { PostService } from '../post.service';
import { CommonModule } from '@angular/common';
import { TimeAgoPipe } from '../../shared/pipes/time-ago.pipe';
import { Router } from '@angular/router';

@Component({
  selector: 'app-post-list',
  standalone: true,
  imports: [CommonModule, TimeAgoPipe],
  templateUrl: './post-list.component.html',
  styleUrl: './post-list.component.css'
})
export class PostListComponent {
  posts: any[] = [];
  page = 0;
  size = 10;
  isLoading = true;

  constructor(private postService: PostService, private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    const userId = this.authService.currentUserId();
    if (userId) {
      this.loadPosts(userId);
    } else {
      console.error('User ID not found in token');
    }
  }

  loadPosts(userId: string): void {
    this.isLoading = true;
    this.postService.getPosts(userId, this.page, this.size).subscribe({
      next: (response) => {
        this.posts = response.content;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading posts:', error);
        this.isLoading = false;
      }
    });
  }

  viewDetails(event: MouseEvent, id: number): void {
    if ((event.target as HTMLElement).closest('button, a, [data-no-navigate]')) {
      return;
    }
    this.router.navigate(['/post', id]);
  }
}
