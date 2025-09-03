import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {environment} from "../../../../environments/environment";

@Injectable({ providedIn: 'root' })
export class HotelService {
  private api = environment.apiBaseUrl;

  getRooms(destination: string, place: string): Observable<string[]> {
    const params = new HttpParams().set('destination', destination).set('place', place);
    return this.http.get<string[]>(`${this.api}/hotel-rates/rooms`, { params });
  }

  constructor(private http: HttpClient) {}
}
