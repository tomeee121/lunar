// src/app/core/services/availability.service.ts
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';

export interface Availability {
  spaceshipId: number;
  spaceshipName: string;
  packageCode: string;
  passengers: number;
  flightCost: number;
  hotelCost: number;
  total: number;
  perPassenger: number;
}

@Injectable({ providedIn: 'root' })
export class AvailabilityService {
  constructor(private api: ApiService) {}

  check(destination: string, date: string, passengers: number, pkg: string, rooms?: string)
    : Observable<Availability[]> {
    const params: any = { date, passengers, pkg };
    if (rooms) params.rooms = rooms;
    return this.api.get<Availability[]>(`/api/${destination}/availability`, params);
  }
}
