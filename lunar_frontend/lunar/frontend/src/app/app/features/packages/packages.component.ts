import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { AvailabilityService, Availability } from '../../core/services/availability.service';

type PackageForm = FormGroup<{
  date: FormControl<string>;
  passengers: FormControl<number>;
}>;

@Component({
  selector: 'app-packages',
  templateUrl: './packages.component.html',
  styleUrls: ['./packages.component.scss']
})
export class PackagesComponent {
  form: PackageForm;
  loading = false;
  availabilities: Availability[] = [];

  constructor(private fb: FormBuilder, private availability: AvailabilityService) {
    this.form = this.fb.nonNullable.group({
      date: this.fb.nonNullable.control('', { validators: [Validators.required] }),
      passengers: this.fb.nonNullable.control(1, { validators: [Validators.required, Validators.min(1)] })
    });
  }

  get f() { return this.form.controls; }

  check(pkg: 'armstrong'|'aldrin'|'conrad') {
    if (this.form.invalid) { this.form.markAllAsTouched(); return; }
    const { date, passengers } = this.form.getRawValue();
    this.loading = true;

    this.availability.check('moon', date, passengers, pkg)
      .subscribe(
        (res: Availability[]) => { this.availabilities = res ?? []; this.loading = false; },
        () => { this.availabilities = []; this.loading = false; }
      );
  }
}
