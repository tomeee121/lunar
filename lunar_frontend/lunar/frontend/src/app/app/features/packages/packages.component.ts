import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TripQuote } from '../../core/models/trips.model';
import { TripsService } from '../../core/services/trips.service';
import { SpaceshipService } from '../../core/services/spaceship.service';
import { SpaceshipModel } from '../../core/models/spaceship.model';
import { Overlay } from '@angular/cdk/overlay';

@Component({
  selector: 'app-packages',
  templateUrl: './packages.component.html',
  styleUrls: ['./packages.component.scss']
})
export class PackagesComponent implements OnInit {
  form: FormGroup;
  loading = false;
  error = '';
  quote: TripQuote | null = null;

  ships: SpaceshipModel[] = [];
  rooms: string[] = [];

  constructor(
    private fb: FormBuilder,
    private tripsSvc: TripsService,
    private shipsSvc: SpaceshipService,
    public overlay: Overlay
  ) {
    this.form = this.fb.group({
      spaceshipId: [null, Validators.required],
      flightDate: [null, Validators.required],
      passengers: [1, [Validators.required, Validators.min(1)]],
      payloadKg: [0, [Validators.required, Validators.min(0)]],
      destination: ['moon', Validators.required],
      place: ['CYCLER', Validators.required],
      room: [null, Validators.required],
      nights: [1, [Validators.required, Validators.min(1)]],
      rooms: [1, [Validators.required, Validators.min(1)]],
    });
  }

  ngOnInit(): void {
    this.shipsSvc.dropdown().subscribe({
      next: list => this.ships = list,
      error: () => this.ships = []
    });
    this.loadRooms();
    this.form.get('destination')!.valueChanges.subscribe(() => this.loadRooms());
    this.form.get('place')!.valueChanges.subscribe(() => this.loadRooms());
  }

  loadRooms(): void {
    const dest = this.form.get('destination')!.value;
    const place = this.form.get('place')!.value;
    if (!dest || !place) { this.rooms = []; this.form.get('room')!.setValue(null); return; }

    this.tripsSvc.rooms(dest, place).subscribe({
      next: r => {
        this.rooms = r;
        if (!r.includes(this.form.get('room')!.value)) this.form.get('room')!.setValue(null);
      },
      error: () => { this.rooms = []; }
    });
  }

  search(): void { this.getQuote(); }

  getQuote(): void {
    if (this.form.invalid) { this.form.markAllAsTouched(); return; }
    this.loading = true; this.error = ''; this.quote = null;

    const v = this.form.value;
    const flightDate = typeof v.flightDate === 'string'
      ? v.flightDate
      : v.flightDate?.toISOString?.().slice(0, 10);

    this.tripsSvc.quote({
      spaceshipId: v.spaceshipId,
      flightDate,
      passengers: v.passengers,
      payloadKg: v.payloadKg,
      destination: v.destination,
      place: v.place,
      room: v.room,
      nights: v.nights,
      rooms: v.rooms
    }).subscribe({
      next: q => { this.quote = q; this.loading = false; },
      error: () => { this.error = 'Quote failed'; this.loading = false; }
    });
  }

  clear(): void {
    this.form.reset({
      spaceshipId: null,
      flightDate: null,
      passengers: 1,
      payloadKg: 0,
      destination: 'moon',
      place: 'CYCLER',
      room: null,
      nights: 1,
      rooms: 1
    });
    this.quote = null; this.error = '';
    this.loadRooms();
  }

  shipById(id?: number | null) {
    return this.ships.find(x => x.id === id!) || null;
  }
}
