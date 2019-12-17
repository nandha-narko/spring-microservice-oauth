import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { ExperienceComponent } from './experience/experience.component';
import { SkillsComponent } from './skills/skills.component';
import { EducationComponent } from './education/education.component';
import { ContactComponent } from './contact/contact.component';
import { SecurityGuard } from './core/guards/security.guard';

const routes: Routes = [
  {
    path : '',
    component: HomeComponent
  },
  {
    path : 'experience',
    component: ExperienceComponent,
    canActivate: [SecurityGuard]
  },
  {
    path : 'education',
    component: EducationComponent,
    canActivate: [SecurityGuard]
  },
  {
    path : 'skills',
    component: SkillsComponent,
    canActivate: [SecurityGuard]
  },
  {
    path : 'contact',
    component: ContactComponent,
    canActivate: [SecurityGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }