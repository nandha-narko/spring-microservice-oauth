import { Component, OnInit } from '@angular/core';
import { SecurityService } from './core/services/security.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  isAuthorized = false;
  userName: string;
  userAvatar: string;
  
  constructor(private securityService: SecurityService, private router: Router) {}

  ngOnInit() {
    this.securityService.initialize();
    this.isAuthorized = this.securityService.isAuthorized();
    if(this.isAuthorized) {
      this.userName = this.securityService.getUserName();
      this.userAvatar = this.securityService.getUserAvatar();
    }
  }

  login() {
    this.securityService.login();
  }

  logout() {
    this.securityService.logout();
    this.router.navigate(['/']);
  }
}
