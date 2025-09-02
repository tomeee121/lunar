import { Component } from '@angular/core';
import { Router } from '@angular/router';
import {AuthService} from "../../../core/services/auth.service";

@Component({
  selector: 'app-register',
  template: `
  <section class="auth">
    <h2>Register</h2>
    <form (ngSubmit)="submit()">
      <input [(ngModel)]="email" name="email" type="email" placeholder="email" required style="padding: 0.3vw 0.3vw"/>
      <input [(ngModel)]="password" name="password" type="password" placeholder="password" required style="padding: 0.3vw 0.3vw"/>
      <br>
      <button mat-raised-button color="accent">Create account</button>
    </form>
    <p *ngIf="error" class="err">{{ error }}</p>
  </section>`,
  styles: [`.auth{max-width:360px;margin:24px auto;color:#fff}.err{color:#ffb}`]
})
export class RegisterComponent {
  email = '';
  password = '';
  error: string | null = null;

  constructor(private auth: AuthService, private router: Router) {}

  submit() {
    this.error = null;
    this.auth.register(this.email, this.password).subscribe({
      next: () => this.router.navigateByUrl('/packages'),
      error: e => this.error = e?.error?.message || 'Register failed'
    });
  }
}
