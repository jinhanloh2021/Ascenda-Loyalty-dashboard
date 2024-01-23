import { PointsBalance } from '@/interfaces/PointsBalance';
import { getEncodedJwt } from '@/lib/utils';

/**
 * Queries the points app to get all the balances for a single user
 * Select * from Points where userId = id;
 * @param id User ID
 * @returns List of balances for each app
 */

const URL = 'https://www.proxy.itsag2t3.com:8001'; //should be .env

const headerFixture = async () => {
  const jwt = await getEncodedJwt();
  const headers = new Headers();
  if (jwt) {
    headers.append('Authorization', `Bearer ${jwt}`);
  }
  return { jwt, headers };
};

// export const getPointsByUserId = async (id: string) => {
//   const { headers } = await headerFixture();
//   const res = await fetch(`${URL}/v1/app/users/${id}/points`, {
//     method: 'GET',
//     headers: headers,
//   });

//   if (res.ok) {
//     const text = await res.text();
//     return text ? JSON.parse(text) : [];
//   } else {
//     throw new Error(`Request failed with status ${res.status}`);
//   }
// };

export const getPointsByUserId = async (id: string) => {
  try {
    const { headers } = await headerFixture();
    const res = await fetch(`${URL}/v1/app/users/${id}/points`, {
      method: 'GET',
      headers: headers,
    });
    return await res.json();
  } catch (error) {
    return [];
  }
};

export const updatePointsByPointsId = async (
  pointsId: string,
  balance: number,
  appName: string,
  userId: string,
  jwt: string | undefined
) => {
  const headers = new Headers();
  headers.append('Authorization', `Bearer ${jwt}`);
  headers.append('Content-Type', 'application/json');
  const reqBody = {
    balance: balance,
    appName: appName,
    userId: userId,
  };
  try {
    const res = await fetch(`${URL}/v1/app/points/${pointsId}`, {
      method: 'PUT',
      headers: headers,
      body: JSON.stringify(reqBody),
    });
    const resBody = await res.json();
    alert(`Points Updated: ${JSON.stringify(resBody)}`);
    return resBody;
  } catch (e) {
    console.log(e);
    throw new Error(`Update points failed: ${JSON.stringify(pointsId)}`);
  }
};
