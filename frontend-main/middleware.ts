import { NextRequest, NextResponse } from 'next/server';
import { decode } from 'next-auth/jwt';
import jwt from 'jsonwebtoken';
import { DecodedJwt } from './lib/utils';
import { RequestCookies } from 'next/dist/compiled/@edge-runtime/cookies';

export { default } from 'next-auth/middleware';

export const config = {
  matcher: [
    '/',
    '/login',
    '/signup',
    '/logs',
    '/logs/:path*',
    '/users',
    '/users/:path*',
    '/roles',
    '/roles/:path*',
    '/approvals',
    '/approvals/:path*',
  ],
};

export const unprotectedPaths = [
  '/api/auth/signin',
  '/api/auth/signup',
  '/login',
  '/signup',
  '/health',
];

export async function middleware(req: NextRequest) {
  const jwtJson = await getJwtJsonFromCookies(req.cookies);
  // console.log(`JWT JSON:\n${JSON.stringify(jwtJson)}`);

  if (unprotectedPaths.includes(req.nextUrl.pathname)) {
    return NextResponse.next();
  }

  if (!jwtJson) {
    return NextResponse.redirect(
      `${process.env.NEXTAUTH_URL ?? 'http://localhost:3000'}/login`
    );
  }

  if (jwtJson) {
    const currentPath = req.nextUrl.pathname;
    if (currentPath === '/users' && !jwtJson.role.includes('user.read')) {
      return NextResponse.redirect(
        `${process.env.NEXTAUTH_URL ?? 'http://localhost:3000'}/unauthorised`
      );
    }
    if (currentPath === '/logs' && !jwtJson.role.includes('logs.read')) {
      return NextResponse.redirect(
        `${process.env.NEXTAUTH_URL ?? 'http://localhost:3000'}/unauthorised`
      );
    }
    if (currentPath === '/roles' && !jwtJson.role.includes('role.read')) {
      return NextResponse.redirect(
        `${process.env.NEXTAUTH_URL ?? 'http://localhost:3000'}/unauthorised`
      );
    }
  }

  return NextResponse.next();
}

const getJwtJsonFromCookies = async (
  cookies: RequestCookies
): Promise<DecodedJwt | null> => {
  try {
    const token = cookies.get(
      process.env.SESSION_COOKIE_NAME ?? 'next-auth.session-token'
    );
    const sessionDecoded = await decode({
      token: token?.value,
      secret: process.env.NEXTAUTH_SECRET ?? '',
    });
    const rawJwt = jwt.decode((sessionDecoded?.accessToken as string) ?? '');
    const jwtJson = JSON.parse(JSON.stringify(rawJwt));
    return jwtJson;
  } catch (e: any) {
    console.log("Error: Can't get JWT from cookies");
  }
  return null;
};
