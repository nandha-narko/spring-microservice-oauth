import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SecurityGuard } from './guards/security.guard';
import { SecurityService } from './services/security.service';
import { OAuthModule } from 'angular-oauth2-oidc';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  imports: [
    CommonModule,
    HttpClientModule,
    OAuthModule.forRoot()
  ],
  declarations: [

  ],
  providers: [
    SecurityGuard,
    SecurityService
  ]
})
export class CoreModule { }
