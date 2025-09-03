import { Component, OnInit } from '@angular/core';
import { SpaceshipService } from '../../core/services/spaceship.service';
import { Page } from '../../core/models/page.model';
import { SpaceshipModel } from '../../core/models/spaceship.model';

@Component({
  selector: 'app-spaceships',
  templateUrl: './spaceships.component.html',
  styleUrls: ['./spaceships.component.scss']
})
export class SpaceshipsComponent implements OnInit {
  loading = false;
  error = '';
  data?: Page<SpaceshipModel>;

  page = 0;
  size = 10;
  sortBy: 'name'|'booster'|'maxCapacity'|'weight' = 'name';
  sortDir: 'asc'|'desc' = 'asc';
  q = '';

  allShips: SpaceshipModel[] = [];
  selectedShipId: number | null = null;
  optPage = 0;
  optTotalPages = 1;

  constructor(private spaceshipSvc: SpaceshipService) {}

  ngOnInit(): void {
    this.load();
    this.loadOptions(0);
  }

  load(page = this.page) {
    this.loading = true;
    this.spaceshipSvc.list({
      page, size: this.size, sortBy: this.sortBy, sortDir: this.sortDir, q: this.q || undefined
    }).subscribe({
      next: (res: Page<SpaceshipModel>) => { this.data = res; this.page = res.number; this.loading = false; },
      error: () => { this.error = 'Load failed'; this.loading = false; }
    });
  }

  changeSort(by: 'name'|'booster'|'maxCapacity'|'weight') {
    if (this.sortBy === by) this.sortDir = this.sortDir === 'asc' ? 'desc' : 'asc';
    else { this.sortBy = by; this.sortDir = 'asc'; }
    this.load(0);
  }

  clearFilters() {
    this.q = '';
    this.selectedShipId = null;
    this.sortBy = 'name';
    this.sortDir = 'asc';
    this.load(0);
  }

  loadOptions(page: number) {
    if (page < 0) page = 0;
    this.optPage = page;
    this.spaceshipSvc.list({ page, size: 10, sortBy: 'name', sortDir: 'asc' })
      .subscribe({
        next: (res: Page<SpaceshipModel>) => {
          this.allShips = res.content;
          this.optTotalPages = res.totalPages || 1;
        },
        error: () => { this.allShips = []; this.optTotalPages = 1; }
      });
  }

  onSelectShip() {
    this.load(0);
  }
}
