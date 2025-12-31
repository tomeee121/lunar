// src/app/core/services/availability.service.ts
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { Availability } from '../models/availability.model';

@Injectable({ providedIn: 'root' })
export class AvailabilityService {
  constructor(private api: ApiService) {}

  check(destination: string, date: string, passengers: number, pkg: string, rooms?: string)
    : Observable<Availability[]> {
    const params: Record<string, string | number> = { date, passengers, pkg };
    if (rooms) params['rooms'] = rooms;
    return this.api.get<Availability[]>(`/api/${destination}/availability`, params);
  }
}
