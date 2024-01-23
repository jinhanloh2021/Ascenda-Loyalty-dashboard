import { Log } from '@/interfaces/Log';
import { getEncodedJwt } from '@/lib/utils';

const URL = 'https://www.proxy.itsag2t3.com:8001'; //should be .env

const headerFixture = async () => {
  const jwt = await getEncodedJwt();
  const headers = new Headers();
  if (jwt) {
    headers.append('Authorization', `Bearer ${jwt}`);
  }
  return { jwt, headers };
};

export const getAllLogs = async () :Promise<Log[]>=> {
  const { headers } = await headerFixture();
  const res = await fetch(`${URL}/v1/app/logs`, {
    method: 'GET',
    headers: headers,
  });
  return await res.json();
};

export const getLogById = async (id: string) => {
  const allLogs = await getAllLogs();
  const logWithId = allLogs.find((log) => log.logsId === id);
  return logWithId || defaultLog;
};

export const defaultLog: Log = 
  {
    logsId: '',
    dateTime: '',
    device: '',
    description: '',
  }