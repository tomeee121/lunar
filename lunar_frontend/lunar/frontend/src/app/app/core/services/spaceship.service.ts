import {Injectable} from '@angular/core';
import {ApiService} from './api.service';
import {Observable} from 'rxjs';
import {Paged, SpaceshipModel} from '../models/spaceship.model';

function normalizePage<T>(res: any): Paged<T> {
  if (Array.isArray(res)) {
    const content = res as T[];
    return { content, number: 0, size: content.length, totalElements: content.length, totalPages: 1 };
  }
  return res as Paged<T>;
}

@Injectable({ providedIn: 'root' })
export class SpaceshipService {
  constructor(private api: ApiService) {}

  list(
    q = '',
    sortBy: 'name'|'booster'|'maxCapacity'|'weight' = 'name',
    sortDir: 'asc'|'desc' = 'asc',
    page = 0,
    size = 10,
    id?: number,
    match: 'contains'|'exact' = 'contains'
  ): Observable<Paged<SpaceshipModel>> {
    const params: any = { sortBy, sortDir, page, size };
    if (id != null) params.id = id; else { params.q = q; params.match = match; }
    return this.api.get<Paged<SpaceshipModel>>('/api/spaceships', params);
  }

  /** get with pager */
  dropdownPage(page = 0, size = 20, q = ''): Observable<Paged<SpaceshipModel>> {
    return this.api.get<Paged<SpaceshipModel>>('/api/spaceships', {
      page, size, sortBy: 'name', sortDir: 'asc', q, match: 'contains'
    });
  }
}
