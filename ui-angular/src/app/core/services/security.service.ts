import { Injectable } from '@angular/core';
import { AuthConfig, OAuthService } from 'angular-oauth2-oidc';
import { HttpClient } from '@angular/common/http';
import * as jwt_decode from 'jwt-decode';

const AUTH_CONFIG: AuthConfig = {
  clientId: "ui-angular",
  loginUrl: "http://localhost:8080/oauth/authorize",
  logoutUrl: "http://localhost:8080/logout",
  redirectUri: window.location.origin,
  scope: "openid profile",
  responseType: "token id_token",
  oidc: false,
  requireHttps: false
}

@Injectable({
  providedIn: 'root'
})
export class SecurityService {

  private userName: string;
  private userAvatar: string;

  constructor(
    private oauthService: OAuthService,
    private http: HttpClient
  ) { }

  public initialize() {
    this.oauthService.configure(AUTH_CONFIG);
    this.oauthService.setStorage(sessionStorage);
    // This method just tries to parse the token(s) within the url when
    // the auth-server redirects the user back to the web-app
    // It doesn't send the user the the login page
    this.oauthService.tryLogin({
        onTokenReceived: (info) => {
            this.decodeToken();
        }            
    });

    if(this.isAuthorized) {
      this.decodeToken();
    }
  }

  public isAuthorized(): boolean {
    return this.oauthService.getAccessToken() !== null && this.oauthService.getAccessToken() !== "";
  }

  public getUserName() {
      return this.userName;
  }

  public getUserAvatar() {
    return this.userAvatar;
  }

  public login() {
      this.oauthService.initImplicitFlow();
  }

  public logout() {
      this.oauthService.logOut(true);
  }

  private decodeToken(){
    const tokenInfo = jwt_decode(this.oauthService.getAccessToken());
    if(tokenInfo) {
      this.userName = tokenInfo.user_name;
      this.userAvatar = tokenInfo.user_avatar;
    }
  }
}
