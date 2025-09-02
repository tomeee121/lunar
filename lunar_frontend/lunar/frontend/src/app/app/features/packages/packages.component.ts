import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TravelPackage } from '../../core/models/package.model';
import { PackageService } from '../../core/services/package.service';

@Component({
  selector: 'app-packages',
  templateUrl: './packages.component.html',
  styleUrls: ['./packages.component.scss']
})
export class PackagesComponent implements OnInit {
  form: FormGroup;

  packages: TravelPackage[] = [];
  loading = false;
  error: string | null = null;

  constructor(private fb: FormBuilder, private packagesSvc: PackageService) {
    this.form = this.fb.group({
      date: [null, Validators.required],
      passengers: [1, [Validators.required, Validators.min(1)]],
    });
  }

  ngOnInit(): void {
    this.loadPackages();
  }

  private loadPackages(): void {
    this.loading = true; this.error = null;
    this.packagesSvc.list().subscribe({
      next: (res) => { this.packages = res ?? []; this.loading = false; },
      error: (e) => { this.error = e?.message ?? 'Failed to load packages'; this.loading = false; }
    });
  }

  search(): void {
    this.form.markAllAsTouched();
    if (this.form.invalid) return;
  }

  clear(): void {
    this.form.reset({ date: null, passengers: 1 });
  }
}
