import { Routes } from '@angular/router';
import { PostCardComponent } from './post/post-card/post-card.component';
import { PostListComponent } from './post/post-list/post-list.component';

export const routes: Routes = [
    {path: '', component: PostListComponent},
    {path: 'post/:id', component: PostCardComponent}
];
