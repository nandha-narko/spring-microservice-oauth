import { Injectable } from '@angular/core';
import { AuthConfig, OAuthService } from 'angular-oauth2-oidc';
import { HttpClient } from '@angular/common/http';

const AUTH_CONFIG: AuthConfig = {
  clientId: "nandhas-web",
  loginUrl: "http://localhost:8080/oauth/authorize",
  logoutUrl: "http://localhost:8080/logout",
  redirectUri: window.location.origin,
  scope: "openid profile",
  oidc: false,
  requireHttps: false
}

@Injectable({
  providedIn: 'root'
})
export class SecurityService {

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
            console.debug('state', info.state);
        }            
    });
  }

  public isAuthorized(): boolean {
    return this.oauthService.getAccessToken() !== null && this.oauthService.getAccessToken() !== "";
  }

  public getUserInfo() {
      return this.http.get("http://localhost:8080/user")
                  .subscribe(data => {
                      debugger
                  });
  }

  public login() {
      this.oauthService.initImplicitFlow();
  }

  public logout() {
      this.oauthService.logOut();
  }

}
