import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ArticleslistComponent } from './articleslist/articleslist.component';
import { ArticleComponent } from './article/article.component';

const routes: Routes = [

  { path: '', component: ArticleslistComponent},
  { path: 'content/:aId', component: ArticleComponent},
  { path: '**', redirectTo: '' }

];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
