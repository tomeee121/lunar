import { Component, OnInit } from '@angular/core';
import { SpaceshipService } from '../../core/services/spaceship.service';
import { Paged, SpaceshipModel } from '../../core/models/spaceship.model';

@Component({
  selector: 'app-spaceships',
  templateUrl: './spaceships.component.html',
  styleUrls: ['./spaceships.component.scss']
})
export class SpaceshipsComponent implements OnInit {
  q = '';
  sortBy: 'name' | 'booster' | 'maxCapacity' = 'name';
  sortDir: 'asc' | 'desc' = 'asc';
  page = 0; size = 10;

  data: Paged<SpaceshipModel> | null = null;
  loading = false;
  error: string | null = null;

  constructor(private svc: SpaceshipService) {}

  ngOnInit(): void { this.load(); }

  load(): void {
    this.loading = true; this.error = null;
    this.svc.list(this.q, this.sortBy, this.sortDir, this.page, this.size).subscribe({
      next: d => { this.data = d; this.loading = false; },
      error: e => { this.error = e.message ?? 'Error'; this.loading = false; }
    });
  }

  changeSort(field: 'name' | 'booster' | 'maxCapacity') {
    this.sortBy = field;
    this.sortDir = this.sortDir === 'asc' ? 'desc' : 'asc';
    this.page = 0;
    this.load();
  }
}
