import { Component, OnInit } from '@angular/core';
import {BookingView} from "../../../core/models/booking.model";
import {BookingService} from "../../../core/services/booking.service";

@Component({
  selector: 'app-booking-history',
  template: `
  <section class="history">
    <h2>Your bookings</h2>
    <p class="error" *ngIf="error">{{ error }}</p>
    <p *ngIf="loading">Loading...</p>
    <table class="result" *ngIf="!loading && !error && items.length">
      <tr><th>Date</th><th>Ship</th><th>Passengers</th><th>Package</th></tr>
      <tr *ngFor="let b of items">
        <td>{{ b.date }}</td>
        <td>{{ b.spaceshipName || ('#'+b.spaceshipId) }}</td>
        <td>{{ b.passengers }}</td>
        <td>{{ b.packageCode || '-' }}</td>
      </tr>
    </table>
    <p *ngIf="!loading && !error && !items.length">No bookings yet.</p>
  </section>`,
  styles: [`.history{max-width:960px;margin:24px auto;color:#fff}
            .result{width:100%;border-collapse:collapse;background:rgba(0,0,0,.28)}
            .result th,.result td{border:1px solid rgba(255,255,255,.18);padding:8px 12px}
            .error{color:#ff6b6b}`]
})
export class BookingHistoryComponent implements OnInit {
  items: BookingView[] = [];
  loading = false;
  error = '';

  constructor(private api: BookingService) {}

  ngOnInit() {
    this.loading = true;
    this.api.mine().subscribe({
      next: v => {
        this.items = v;
        this.loading = false;
      },
      error: e => {
        this.error = e?.error?.message || 'Failed to load bookings';
        this.loading = false;
      }
    });
  }
}
