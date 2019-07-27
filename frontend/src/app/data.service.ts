import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import { of } from 'rxjs/observable/of';
import { catchError, map, tap } from 'rxjs/operators';
import { PagerService } from './pager.service';

@Injectable()
export class DataService {

result: Response;


  constructor(
    private _http: Http
  ) { }

  getArticles(){
              console.log("ds here");
             return this._http.get("/api/emagazine/articles/1")
              .map(result => this.result = result.json());
  }
  getAllArticles(){
              console.log("ds tut");
             return this._http.get("/api/emagazine/articles")
              .map(result => this.result = result.json());
  }
  getContentById(aId: string){
               return this._http.get("/api/emagazine/article/" + aId)
                .map(result => this.result = result.json());
  }
}
