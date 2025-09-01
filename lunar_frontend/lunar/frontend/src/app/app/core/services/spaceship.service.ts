import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { Observable } from 'rxjs';
import { Paged, SpaceshipModel } from '../models/spaceship.model';

@Injectable({ providedIn: 'root' })
export class SpaceshipService {
  constructor(private api: ApiService) {}

  list(q = '', sortBy = 'name', sortDir = 'asc', page = 0, size = 10)
    : Observable<Paged<SpaceshipModel>> {
    return this.api.get<Paged<SpaceshipModel>>('/api/spaceships', { q, sortBy, sortDir, page, size });
  }
}
