import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Paged, SpaceshipModel } from '../models/spaceship.model';
import {environment} from "../../../../environments/environment";

const API = environment.apiBaseUrl;

@Injectable({ providedIn: 'root' })
export class SpaceshipService {
  constructor(private http: HttpClient) {}

  list(
    q: string,
    sortBy: 'name' | 'booster' | 'maxCapacity' | 'weight',
    sortDir: 'asc' | 'desc',
    page: number,
    size: number,
    id?: number | null,
    match: 'contains' | 'exact' = 'contains'
  ): Observable<Paged<SpaceshipModel>> {
    let params = new HttpParams()
      .set('sortBy', sortBy)
      .set('sortDir', sortDir)
      .set('page', page)
      .set('size', size);

    if (id != null) {
      params = params.set('id', String(id)).set('match', 'exact');
    } else if (q) {
      params = params.set('q', q).set('match', match);
    }

    return this.http.get<Paged<SpaceshipModel>>(`${API}/spaceships`, { params });
  }

  dropdown(): Observable<SpaceshipModel[]> {
    return this.http.get<SpaceshipModel[]>(`${API}/spaceships/dropdown`);
  }

  dropdownPage(page: number, size: number, q?: string): Observable<Paged<SpaceshipModel>> {
    let params = new HttpParams()
      .set('page', page)
      .set('size', size)
      .set('sortBy', 'name')
      .set('sortDir', 'asc');

    if (q && q.trim()) {
      params = params.set('q', q.trim()).set('match', 'contains');
    }

    return this.http.get<Paged<SpaceshipModel>>(`${API}/spaceships`, { params });
  }
}
