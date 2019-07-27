import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Article } from '../model/article';
import { DataService } from '../data.service';

@Component({
  selector: 'app-articleslist',
  templateUrl: './articleslist.component.html',
  styleUrls: ['./articleslist.component.css']
})
export class ArticleslistComponent implements OnInit {
  articles: Article[];
  constructor(
     private route: ActivatedRoute,
     private router: Router,
     private dataService: DataService
  ) { }

  ngOnInit() {

        this.dataService.getArticles()
        .subscribe(a => {
           this.articles = a;
           console.log(this.articles);
          });
        console.log('articles');
  }

}
