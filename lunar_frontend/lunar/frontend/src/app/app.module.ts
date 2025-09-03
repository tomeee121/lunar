import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { Overlay, ScrollStrategy } from '@angular/cdk/overlay';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';

import { PackagesComponent } from './app/features/packages/packages.component';
import { SpaceshipsComponent } from './app/features/spaceships/spaceships.component';
import { LoginComponent } from './app/features/auth/login/login.component';
import { RegisterComponent } from './app/features/auth/register/register.component';

// Angular Material
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import {MAT_SELECT_SCROLL_STRATEGY, MatSelectModule} from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatTableModule } from '@angular/material/table';

export function matSelectScrollStrategyFactory(overlay: Overlay): () => ScrollStrategy {
  return () => overlay.scrollStrategies.reposition();
}

@NgModule({
  declarations: [
    AppComponent,
    PackagesComponent,
    SpaceshipsComponent,
    LoginComponent,
    RegisterComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,

    AppRoutingModule,

    MatSidenavModule,
    MatListModule,
    MatIconModule,
    MatToolbarModule,
    MatButtonModule,
    MatCardModule,
    MatProgressBarModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatTableModule
  ],
  providers: [
    { provide: MAT_SELECT_SCROLL_STRATEGY, useFactory: matSelectScrollStrategyFactory, deps: [Overlay] }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
