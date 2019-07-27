import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Http, Headers, RequestOptions, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map'
import { Article } from '../model/article';
import { DataService } from '../data.service';
import { PagerService } from '../pager.service';

@Component({
  moduleId: module.id,
  selector: 'app-articleslist',
  templateUrl: './articleslist.component.html',
  styleUrls: ['./articleslist.component.css']
})
export class ArticleslistComponent implements OnInit {
  articles: Article[];
  constructor(
     private http: Http,
     private route: ActivatedRoute,
     private router: Router,
     private dataService: DataService,
     private pagerService: PagerService
  ) { }
    // array of all items to be paged
    private allItems: any[];

    // pager object
    pager: any = {};

    // paged items
    pagedItems: any[];
  ngOnInit() {

        this.dataService.getAllArticles()
        .subscribe(a => {
           this.allItems = a;
           this.articles = a;
           this.setPage(1);
           //console.log(this.allItems);
          });
        //console.log('articles');
  }

  setPage(page: number) {
      // get pager object from service
      this.pager = this.pagerService.getPager(this.allItems.length, page);

      // get current page of items
      this.pagedItems = this.allItems.slice(this.pager.startIndex, this.pager.endIndex + 1);
      console.log("allitems");
      console.log(this.allItems);

      console.log("pagedItems");
      console.log(this.pagedItems);
  }

}
