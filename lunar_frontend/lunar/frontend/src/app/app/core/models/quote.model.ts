export interface TripQuoteRequest {
  spaceshipId: number;
  flightDate: string;         // ISO (yyyy-MM-dd)
  passengers: number;
  destination: string;        // np. "moon"
  place: string;              // "CYCLER" | "SURFACE" (albo inne z BE)
  room: string;               // np. "armstrong"
  nights: number;
}

export interface MoneyDTO { currency: string; amount: number; }

export interface TripQuote {
  spaceshipId: number;
  spaceshipName: string;
  booster: string;
  fuelType: string;
  flightDate: string;
  passengers: number;
  destination: string;
  place: string;
  room: string;
  nights: number;

  payloadKg: number;
  estFuelKg: number;
  rooms: number;

  fuelCost: MoneyDTO;
  hotelCost: MoneyDTO;
  serviceFee: MoneyDTO;
  govTax: MoneyDTO;
  subtotal: MoneyDTO;
  total: MoneyDTO;
}
