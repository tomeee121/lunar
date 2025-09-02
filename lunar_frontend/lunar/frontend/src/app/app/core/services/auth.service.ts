import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthResponse } from '../models/auth.model';
import { Observable, tap } from 'rxjs';
import {environment} from "../../../../environments/environment";

const TOKEN_KEY = 'jwt';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private api = environment.apiBaseUrl;

  constructor(private http: HttpClient) {}

  register(email: string, password: string): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.api}/auth/register`, { email, password })
      .pipe(tap(res => this.setToken(res.token)));
  }

  login(email: string, password: string): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.api}/auth/login`, { email, password })
      .pipe(tap(res => this.setToken(res.token)));
  }

  setToken(t?: string) { if (t) localStorage.setItem(TOKEN_KEY, t); }
  token(): string | null { return localStorage.getItem(TOKEN_KEY); }
  logout() { localStorage.removeItem(TOKEN_KEY); }
  isAuth(): boolean { return !!this.token(); }
}
