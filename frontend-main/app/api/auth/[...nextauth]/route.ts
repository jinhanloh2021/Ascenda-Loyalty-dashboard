import NextAuth, { NextAuthOptions, User } from 'next-auth';
import { AdapterUser } from 'next-auth/adapters';
import { JWT } from 'next-auth/jwt';
import CredentialsProvider from 'next-auth/providers/credentials';

// /app/api/auth/[...nextauth]/route.ts

export const authOptions: NextAuthOptions = {
  providers: [
    CredentialsProvider({
      name: 'Credentials',
      credentials: {
        email: {
          label: 'Email',
          type: 'text',
          placeholder: 'your_email@gmail.com',
        },
        password: { label: 'Password', type: 'password' },
      },
      async authorize(credentials, req) {
        const email = credentials?.email;
        const password = credentials?.password;
        const res = await fetch(
          'https://www.proxy.itsag2t3.com:8001/v1/app/auth/signin',
          {
            method: 'POST',
            body: JSON.stringify({
              email: email,
              password: password,
            }),
            headers: { 'Content-Type': 'application/json' },
          }
        );
        const user = await res.json();
        if (res.ok && user) {
          return user;
        } else {
          return null;
        }
      },
    }),
  ],
  session: {
    strategy: 'jwt',
    maxAge: 60 * 60, // 1 hour
  },
  callbacks: {
    async jwt({ token, user }: { token: JWT; user: User | AdapterUser | any }) {
      if (user) {
        token.accessToken = user.token ?? ''; // will work
      }
      return token;
    },
    async session({ session, user, token }) {
      if (session) {
        session = Object.assign({}, session, {
          token: token,
        });
      }
      return session;
    },
  },
  pages: {
    signIn: '/login',
  },
};

const handler = NextAuth(authOptions);
export { handler as GET, handler as POST };
