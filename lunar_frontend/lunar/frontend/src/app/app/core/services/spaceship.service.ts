import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SpaceshipModel } from '../models/spaceship.model';
import { Page } from '../models/page.model';
import {environment} from "../../../../environments/environment";

@Injectable({ providedIn: 'root' })
export class SpaceshipService {
  private readonly API = 'spaceships';
  private readonly baseUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) {}

  list(params: {
    page: number; size: number; sortBy: string; sortDir: 'asc'|'desc'; q?: string;
  }): Observable<Page<SpaceshipModel>> {
    let p = new HttpParams()
      .set('page', params.page)
      .set('size', params.size)
      .set('sortBy', params.sortBy)
      .set('sortDir', params.sortDir);
    if (params.q) p = p.set('q', params.q);

    return this.http.get<Page<SpaceshipModel>>(`${this.baseUrl}/${this.API}`, { params: p });
  }

  dropdown(): Observable<SpaceshipModel[]> {
    return this.http.get<SpaceshipModel[]>(`${this.baseUrl}/${this.API}/all`);
  }
}
