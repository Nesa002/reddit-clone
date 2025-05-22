import { Component } from '@angular/core';
import { AuthService } from '../../auth/auth.service';
import { PostService } from '../post.service';
import { CommonModule } from '@angular/common';
import { TimeAgoPipe } from '../../shared/pipes/time-ago.pipe';
import { Router } from '@angular/router';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import { FilterType } from '../enum/post.filter.type';

@Component({
  selector: 'app-post-list',
  standalone: true,
  imports: [CommonModule, TimeAgoPipe, MatProgressSpinnerModule],
  templateUrl: './post-list.component.html',
  styleUrl: './post-list.component.css'
})
export class PostListComponent {
  filter: FilterType = FilterType.NEW;
  isDropdownOpen = false;
  filterOptions = Object.values(FilterType);

  posts: any[] = [];
  page = 0;
  size = 10;
  isLoading = true;
  lastPage = false;

  constructor(private postService: PostService, private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.loadMorePosts();
  }

  ngAfterViewInit(): void {
    window.addEventListener('scroll', this.onScroll, true);
  }

  ngOnDestroy(): void {
    window.removeEventListener('scroll', this.onScroll, true);
  }

  loadMorePosts(): void {
    if (this.lastPage) return;

    const userId = this.authService.currentUserId();
    if (!userId) {
      console.error('User ID not found in token');
      return
    }

    this.isLoading = true;
    this.postService.getPosts(userId, this.page, this.size, this.filter).subscribe({
      next: (response) => {
        this.posts = [...this.posts, ...response.content];
        this.lastPage = response.last;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading posts:', error);
        this.isLoading = false;
      }
    });
  }

  toggleDropdown() {
    this.isDropdownOpen = !this.isDropdownOpen;
  }

  selectFilter(filter: FilterType) {
    this.filter = filter;
    this.isDropdownOpen = false;
    this.posts = [];
    this.lastPage = false;
    this.page = 0;
    this.loadMorePosts();
  }

  viewDetails(event: MouseEvent, id: number): void {
    if ((event.target as HTMLElement).closest('button, a, [data-no-navigate]')) {
      return;
    }
    this.router.navigate(['/post', id]);
  }

  onScroll = (): void => {
    const scrollTop = window.scrollY || document.documentElement.scrollTop;
    const viewportHeight = window.innerHeight;
    const fullHeight = document.documentElement.scrollHeight;

    if (scrollTop + viewportHeight >= fullHeight - 100 && !this.isLoading && !this.lastPage) {
      this.page++;
      this.loadMorePosts();
    } 
  }
  getLabel(filter: string): string {
  return filter
    .replace('TOP_', 'Top of ')
    .replace('_', ' ')
    .toLowerCase()
    .replace(/\b\w/g, char => char.toUpperCase());
}
}


