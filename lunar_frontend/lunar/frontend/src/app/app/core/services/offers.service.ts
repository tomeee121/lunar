import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TripQuote, TripQuoteRequest } from '../models/quote.model';
import {environment} from "../../../../environments/environment";

@Injectable({ providedIn: 'root' })
export class OffersService {
  private api = environment.apiBaseUrl;

  constructor(private http: HttpClient) {}

  quote(req: TripQuoteRequest): Observable<TripQuote> {
    return this.http.post<TripQuote>(`${this.api}/offers/quote`, req);
  }

  quoteGet(req: TripQuoteRequest): Observable<TripQuote> {
    const params = new HttpParams()
      .set('spaceshipId', req.spaceshipId)
      .set('date', req.flightDate)
      .set('passengers', req.passengers)
      .set('destination', req.destination)
      .set('place', req.place)
      .set('room', req.room)
      .set('nights', req.nights);
    return this.http.get<TripQuote>(`${this.api}/offers/quote`, { params });
  }
}
