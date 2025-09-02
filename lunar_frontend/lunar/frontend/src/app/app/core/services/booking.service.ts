import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CreateBookingRequest, BookingView } from '../models/booking.model';
import { Observable } from 'rxjs';
import {environment} from "../../../../environments/environment";

@Injectable({ providedIn: 'root' })
export class BookingService {
  private api = environment.apiBaseUrl;

  constructor(private http: HttpClient) {}

  create(req: CreateBookingRequest): Observable<BookingView> {
    return this.http.post<BookingView>(`${this.api}/bookings`, req);
  }

  mine(): Observable<BookingView[]> {
    return this.http.get<BookingView[]>(`${this.api}/bookings/me`);
  }
}
