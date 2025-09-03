import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TripQuote } from '../models/trips.model';
import {environment} from "../../../../environments/environment";

@Injectable({ providedIn: 'root' })
export class TripsService {
  private readonly API = 'trips';
  private readonly baseUrl = environment.apiBaseUrl;


  constructor(private http: HttpClient) {}

  rooms(destination: string, place: string): Observable<string[]> {
    const params = new HttpParams().set('destination', destination).set('place', place);
    return this.http.get<string[]>(`${this.baseUrl}/${this.API}/rooms`, { params });
  }

  quote(body: {
    spaceshipId: number;
    flightDate: string;
    passengers: number;
    payloadKg: number;
    destination: string;
    place: string;
    room: string;
    nights: number;
    rooms: number;
  }): Observable<TripQuote> {
    return this.http.post<TripQuote>(`${this.baseUrl}/${this.API}/quote`, body);
  }
}
