export interface SpaceshipModel {
  id: number;
  name: string;
  booster: string;
  weight: number;
  maximumCapacity: number;
  fuelType?: string;
}

export interface Paged<T> {
  content: T[];
  number: number;
  size: number;
  totalElements: number;
  totalPages: number;
}
