import { getDecodedJwt } from '@/lib/utils';
import React from 'react';

type Props = {};

export default async function Approval({}: Props) {
  const user = await getDecodedJwt();
  return <div className='break-words'>{JSON.stringify(user)}</div>;
}
