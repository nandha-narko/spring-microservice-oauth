import { Component, OnInit } from '@angular/core';
import { SecurityService } from './core/services/security.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  isAuthorized = false;
  
  constructor(private securityService: SecurityService) {}

  ngOnInit() {
    this.securityService.initialize();
    this.isAuthorized = this.securityService.isAuthorized();
  }
}
