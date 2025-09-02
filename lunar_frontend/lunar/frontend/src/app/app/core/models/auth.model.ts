export interface UserModel {
  id: number;
  email: string;
  role: 'USER' | 'ADMIN';
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  email: string;
  password: string;
}

export interface AuthResponse {
  token: string;
  user: UserModel;
}

export interface JWTPayload {
  sub: string;          // email or username
  exp: number;          // epoch seconds
  iat?: number;         // epoch seconds
  roles?: string[];
  userId?: number;
}

export type AuthStatus = 'anonymous' | 'loading' | 'authenticated' | 'error';

/** localStorage */
export interface StoredAuth {
  token: string;
  user: UserModel;
  expiresAt: number;    // epoch ms
}
