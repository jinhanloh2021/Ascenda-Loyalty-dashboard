import { Log } from '@/interfaces/Log';
import { getLogById } from '@/api/fetchLogs';


import React from 'react';

type Props = {
  params: {
    id: string;
  };
};

export default async function LogsIdPage({ params }: Props) {
  const log: Log = await getLogById(params.id);

  return (
    <>
      <div className='w-[30rem]'>
        <div className='mb-4 grid grid-cols-4 gap-x-4'>
          <div></div>
          <h1 className='text-2xl font-bold mb-2 col-span-3'>
            Log Information
          </h1>
        </div>
        <div className='mb-4 grid grid-cols-4 gap-x-4'>
          <div></div>
          <h2 className='text-base font-semibold mb-2 col-span-3'>LogId</h2>
          <div></div>
          <span className='text-sm mb-4 col-span-3'>{log.logsId}</span>
          <div></div>
          <h2 className='text-base font-semibold mb-2 col-span-3'>Datetime</h2>
          <div></div>
          <span className='text-sm mb-4 col-span-3'>{log.dateTime}</span>
          <div></div>
          <h2 className='text-base font-semibold mb-2 col-span-3'>Device</h2>
          <div></div>
          <span className='text-sm mb-4 col-span-3'>{log.device}</span>
          <div></div>
          <h2 className='text-base font-semibold mb-2 col-span-3'>Description</h2>
          <div></div>
          <span className='text-sm mb-2 col-span-3'>{log.description}</span>
        </div>
      </div>
    </>
  );
}
