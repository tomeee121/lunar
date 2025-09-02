export interface CreateBookingRequest {
  spaceshipId: number;
  date: string;          // 'YYYY-MM-DD'
  passengers: number;
  passengerName?: string;
  packageCode?: string;
}

export interface BookingView {
  id: number;
  passengerName: string;
  passengers: number;
  packageCode?: string;
  date: string;
  spaceshipId: number;
  spaceshipName?: string;
}
