export interface Money {
  amount: number;
  currency: string;
}

export interface TripQuote {
  spaceshipName: string;
  booster: string;
  fuelType: string;
  flightDate: string;
  passengers: number;
  payloadKg: number;
  estFuelKg: number;

  destination: string;
  place: string;
  room: string;
  nights: number;
  rooms: number;

  fuelCost: Money;
  hotelCost: Money;
  serviceFee: Money;
  govTax: Money;
  subtotal: Money;
  total: Money;
}
