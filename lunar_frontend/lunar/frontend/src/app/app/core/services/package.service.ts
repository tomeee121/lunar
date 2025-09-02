import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { TravelPackage } from '../models/package.model';

@Injectable({ providedIn: 'root' })
export class PackageService {
  constructor(private api: ApiService) {}

  list(): Observable<TravelPackage[]> {
    return this.api.get<TravelPackage[]>('/packages');
  }
}
