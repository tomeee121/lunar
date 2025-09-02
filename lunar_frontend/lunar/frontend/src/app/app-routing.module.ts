import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./app/features/home/home.component";
import {SpaceshipsComponent} from "./app/features/spaceships/spaceships.component";
import {PackagesComponent} from "./app/features/packages/packages.component";
import {LoginComponent} from "./app/features/auth/login/login.component";
import {RegisterComponent} from "./app/features/auth/register/register.component";
import {BookingHistoryComponent} from "./app/features/bookings/booking-history/booking-history.component";

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'spaceships', component: SpaceshipsComponent },
  { path: 'packages', component: PackagesComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'bookings', component: BookingHistoryComponent },
  { path: '**', redirectTo: '' }
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
