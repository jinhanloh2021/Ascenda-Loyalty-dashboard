import { getDecodedJwt, getEncodedJwt } from '@/lib/utils';

export default async function Home() {
  const jwt = await getDecodedJwt();
  const encodedJwt = await getEncodedJwt();
  return (
    <main className='break-words max-w-[80vw]'>{JSON.stringify(jwt)}</main>
  );
}
