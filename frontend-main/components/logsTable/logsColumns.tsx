'use client';
import { ColumnDef } from '@tanstack/react-table';
import { Button, buttonVariants } from '@/components/ui/button';
import { ArrowUpDown } from 'lucide-react';
import { ArrowTopRightIcon } from '@radix-ui/react-icons';
import Link from 'next/link';
import { Log } from '@/interfaces/Log';

export const logsColumns: ColumnDef<Log>[] = [
  {
    accessorKey: 'logsId',
    header: 'Logs ID',
  },
  {
    accessorKey: 'dateTime',
    header: ({ column }) => {
      return (
        <Button
          variant='ghost'
          onClick={() => {
            column.toggleSorting(column.getIsSorted() === 'asc');
          }}
        >
          Datetime
          <ArrowUpDown className='ml-2 h-4 w-4' />
        </Button>
      );
    },
    cell: ({ row }) => {
      return new Date(Number(row.getValue('dateTime'))).toLocaleString(
        'en-GB',
        {
          timeZone: 'Asia/Singapore',
        }
      );
    },
  },
  {
    accessorKey: 'device',
    header: 'Device',
  },
  {
    accessorKey: 'description', //todo: fix column size of description
    header: 'Description',
    cell: ({ row }) => {
      const value: string = row.getValue('description') as string;
      return value.length <= 24 ? value : value.substring(0, 24).concat('...');
    },
  },
  {
    id: 'actions',
    cell: ({ row }) => (
      <Link
        className={buttonVariants({ variant: 'ghost', size: 'icon' })}
        href={`/logs/${row.getValue('logsId')}`}
      >
        <ArrowTopRightIcon />
      </Link>
    ),
  },
];