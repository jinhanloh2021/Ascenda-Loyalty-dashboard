import { type ClassValue, clsx } from 'clsx';
import { Session } from 'next-auth';
import { twMerge } from 'tailwind-merge';
import { getServerSession } from 'next-auth/next';
import { authOptions } from '@/app/api/auth/[...nextauth]/route';
import jwt from 'jsonwebtoken';

export type DecodedJwt = {
  role: string[];
  name: string;
  userId: string;
  sub: string;
  iat: string;
  exp: string;
};

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs));
}

export async function getDecodedJwt(): Promise<DecodedJwt> {
  const session: (Session & { token: { accessToken: string } }) | null =
    await getServerSession(authOptions);
  const rawJwt = jwt.decode(session?.token.accessToken ?? '');
  const jwtJson = JSON.parse(JSON.stringify(rawJwt));
  return jwtJson;
}

export async function getEncodedJwt() {
  const session: (Session & { token: { accessToken: string } }) | null =
    await getServerSession(authOptions);
  return session?.token.accessToken;
}
