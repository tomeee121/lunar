export interface SpaceshipModel {
  id: number;
  name: string;
  booster: string;
  weight: number;
  maximumCapacity: number;
  fuelTypeName?: string;
}

export interface Paged<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}
