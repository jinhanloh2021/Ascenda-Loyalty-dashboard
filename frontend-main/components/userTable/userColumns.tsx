'use client';
import { User } from '@/interfaces/User';
import { ColumnDef } from '@tanstack/react-table';
import { Button, buttonVariants } from '@/components/ui/button';
import { ArrowUpDown } from 'lucide-react';
import { ArrowTopRightIcon } from '@radix-ui/react-icons';
import Link from 'next/link';

export const userColumns: ColumnDef<User>[] = [
  {
    accessorKey: 'userId',
    header: 'User ID',
  },
  {
    accessorKey: 'name',
    header: 'Name',
  },
  {
    accessorKey: 'email',
    header: 'Email',
  },
  {
    accessorKey: 'totalPoints',
    header: ({ column }) => {
      return (
        <Button
          variant='ghost'
          onClick={() => {
            column.toggleSorting(column.getIsSorted() === 'asc');
          }}
        >
          Points
          <ArrowUpDown className='ml-2 h-4 w-4' />
        </Button>
      );
    },
  },
  {
    accessorKey: 'role',
    header: 'Role',
  },
  {
    id: 'actions',
    cell: ({ row }) => (
      <Link
        className={buttonVariants({ variant: 'ghost', size: 'icon' })}
        href={`/users/${row.getValue('userId')}`}
      >
        <ArrowTopRightIcon />
      </Link>
    ),
  },
];