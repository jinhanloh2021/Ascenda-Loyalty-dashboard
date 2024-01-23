import { UserSchema } from '@/components/userForms/enrolUserForm';
import { AddUserRes, User, UserRes } from '@/interfaces/User';
import { getEncodedJwt } from '@/lib/utils';
import { signIn } from 'next-auth/react';

const URL = 'https://www.proxy.itsag2t3.com:8001'; //should be .env

const headerFixture = async () => {
  const jwt = await getEncodedJwt();
  const headers = new Headers();
  if (jwt) {
    headers.append('Authorization', `Bearer ${jwt}`);
  }
  return { jwt, headers };
};

export const getAllUsers = async (): Promise<UserRes[]> => {
  const { headers } = await headerFixture();
  const res = await fetch(`${URL}/v1/app/users`, {
    method: 'GET',
    headers: headers,
  });
  return await res.json();
};

export const getUserById = async (id: string) => {
  const { headers } = await headerFixture();
  const res = await fetch(`${URL}/v1/app/users/${id}`, {
    method: 'GET',
    headers: headers,
  });
  return await res.json();
};

export const addUser = async (
  data: UserSchema,
  jwt: string | undefined
): Promise<AddUserRes> => {
  const headers = new Headers();
  headers.append('Authorization', `Bearer ${jwt}`);
  headers.append('Content-Type', 'application/json');
  try {
    const res = await fetch(`${URL}/v1/app/users`, {
      method: 'POST',
      headers: headers,
      body: JSON.stringify(data),
    });
    const credentials: AddUserRes = await res.json();
    alert(`User created!\nRecord this details: ${JSON.stringify(credentials)}`);
    return credentials;
  } catch (e) {
    console.log(e);
    throw new Error(`Add user failed: ${JSON.stringify(data)}`);
  }
};

export const signUp = async (name: string, email: string, password: string) => {
  const headers = new Headers();
  headers.append('Content-Type', 'application/json');
  const body = JSON.stringify({
    name: name,
    email: email,
    password: password,
  });
  console.log(body);
  const res = await fetch(`${URL}/v1/app/auth/signup`, {
    method: 'POST',
    headers: headers,
    body: body,
  });
  const resJson = await res.json();
  console.log(`Res json: ${resJson}`);

  signIn('credentials', {
    email: email,
    password: password,
    callbackUrl: process.env.NEXTAUTH_URL ?? 'http://localhost:3000',
  });
};

export const updateUserById = async (
  id: string,
  data: UserSchema,
  jwt: string | undefined
) => {
  const headers = new Headers();
  headers.append('Authorization', `Bearer ${jwt}`);
  headers.append('Content-Type', 'application/json');
  try {
    const res = await fetch(`${URL}/v1/app/users/${id}`, {
      method: 'PUT',
      headers: headers,
      body: JSON.stringify(data),
    });
    const userRes: UserRes = await res.json();
    alert(`User updated!\nUser details: ${JSON.stringify(userRes)}`);
    return userRes;
  } catch (e) {
    console.log(e);
    throw new Error(`Update user failed: ${JSON.stringify(data)}`);
  }
};
