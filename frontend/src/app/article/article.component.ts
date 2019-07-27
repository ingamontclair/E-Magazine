import { Component, OnInit } from '@angular/core';
import { Article } from '../model/article';
import { Router, ActivatedRoute } from '@angular/router';
import { DataService } from '../data.service';
@Component({
  selector: 'app-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.css']
})
export class ArticleComponent implements OnInit {
aId : string;
article : Article;
  constructor(
     private route: ActivatedRoute,
     private router: Router,
     private dataService: DataService
    ) { }




  ngOnInit() {
        this.route.params.subscribe(params => {
           this.aId = params['aId']
        });

        this.dataService.getContentById(this.aId)
        .subscribe(b => {
           this.article = b;
          });
        console.log('article');
  }

}
