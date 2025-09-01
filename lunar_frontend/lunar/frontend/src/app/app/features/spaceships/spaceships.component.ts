import { Component, OnInit } from '@angular/core';
import { SpaceshipService } from '../../core/services/spaceship.service';
import {Paged, SpaceshipModel} from "../../core/models/spaceship.model";

const LAST_SHIP_KEY = 'lastSelectedShipId';

@Component({
  selector: 'app-spaceships',
  templateUrl: './spaceships.component.html',
  styleUrls: ['./spaceships.component.scss']
})
export class SpaceshipsComponent implements OnInit {
  q = '';
  sortBy: 'name'|'booster'|'maxCapacity'|'weight' = 'name';
  sortDir: 'asc'|'desc' = 'asc';
  page = 0;
  size = 10;

  data: Paged<SpaceshipModel> | null = null;
  allShips: SpaceshipModel[] = [];
  selectedShipId: number | null = null;

  loading = false;
  error: string | null = null;

  displayedColumns = ['name','booster','maximumCapacity','fuelType'];

  optPage = 0;
  optSize = 20;
  optTotalPages = 1;
  optQ = '';

  constructor(private svc: SpaceshipService) {}

  ngOnInit(): void {
    // 1) dropdown list
    this.svc.dropdownPage(this.optPage, this.optSize, this.optQ).subscribe({
      next: p => {
        this.allShips = p.content ?? [];
        this.optTotalPages = p.totalPages ?? 1;

        // 2) get last selected item
        const saved = localStorage.getItem(LAST_SHIP_KEY);
        if (saved) {
          const id = Number(saved);
          if (this.allShips.some(s => s.id === id)) {
            this.selectedShipId = id;
          } else {
            localStorage.removeItem(LAST_SHIP_KEY);
          }
        }

        // 3) load ships
        this.load();
      },
      error: () => { this.allShips = []; this.optTotalPages = 1; this.load(); }
    });
  }

  load(): void {
    this.loading = true; this.error = null;

    const id = this.selectedShipId ?? undefined;
    const query = id ? '' : (this.q?.trim() || '');
    const match: 'contains'|'exact' = id ? 'exact' : 'contains';

    this.svc.list(query, this.sortBy, this.sortDir, this.page, this.size, id, match)
      .subscribe({
        next: d => { this.data = d; this.loading = false; },
        error: e => { this.error = e?.message ?? 'Error'; this.data = null; this.loading = false; }
      });
  }

  changeSort(field: 'name'|'booster'|'maxCapacity'|'weight') {
    this.sortBy = field === this.sortBy ? this.sortBy : field;
    this.sortDir = field === this.sortBy ? (this.sortDir === 'asc' ? 'desc' : 'asc') : 'asc';
    this.page = 0; this.load();
  }

  onSelectShip() {
    // update storage item chosen
    if (this.selectedShipId != null) {
      localStorage.setItem(LAST_SHIP_KEY, String(this.selectedShipId));
    } else {
      localStorage.removeItem(LAST_SHIP_KEY);
    }
    this.page = 0;
    this.load();
  }

  clearFilters() {
    this.q = '';
    this.selectedShipId = null;
    localStorage.removeItem(LAST_SHIP_KEY);
    this.page = 0;
    this.load();
  }

  loadOptions(page: number) {
    if (page < 0 || page > this.optTotalPages - 1) return;
    this.optPage = page;
    this.svc.dropdownPage(this.optPage, this.optSize, this.optQ).subscribe({
      next: p => { this.allShips = p.content ?? []; this.optTotalPages = p.totalPages ?? 1; },
      error: () => { this.allShips = []; this.optTotalPages = 1; }
    });
  }
}
