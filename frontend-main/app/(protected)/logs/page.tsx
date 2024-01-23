import { getAllLogs } from '@/api/fetchLogs';
import { logsColumns } from '@/components/logsTable/logsColumns';
import LogsTable from '@/components/logsTable/logsTable';
import React from 'react';

type Props = {};

export default async function Logs({}: Props) {
  const allLogs = await getAllLogs();

  return (
    <>
      <div className='flex flex-row justify-between items-center'>
        <h1 className='text-2xl font-bold'>Logs</h1>
      </div>
      <LogsTable columns={logsColumns} data={allLogs} />
    </>
  );
}
