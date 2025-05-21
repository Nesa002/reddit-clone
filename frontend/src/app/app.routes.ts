import { Routes } from '@angular/router';
import { PostCardComponent } from './post/post-card/post-card.component';
import { PostListComponent } from './post/post-list/post-list.component';
import { RoleGuardService } from './auth/guard/role-guard.service';

export const routes: Routes = [
    {path: '', component: PostListComponent, canActivate: [RoleGuardService], data: { requiredRole: ['USER']}},
    {path: 'post/:id', component: PostCardComponent, canActivate: [RoleGuardService], data: { requiredRole: ['USER']}}
];
