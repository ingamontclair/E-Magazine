import { Component } from '@angular/core';
// Import the DataService
import { DataService } from './data.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  title ='app';


  // Create an instance of the DataService through dependency injection
  constructor(private _dataService: DataService) {

  }
}
