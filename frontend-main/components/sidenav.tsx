'use client';
import { cn } from '@/lib/utils';
import Link from 'next/link';
import { usePathname } from 'next/navigation';
import React from 'react';

type Props = {};

export default function SideNav({}: Props) {
  return (
    <aside className='text-blue-800 w-[10rem] col-span-2 row-span-10 mt-4'>
      <div className='flex flex-col justify-start sticky top-[68px] left-0'>
        <NavData href='/users'>Users</NavData>
        <NavData href='/logs'>Logs</NavData>
        <NavData href='/roles'>Roles</NavData>
        <NavData href='/approvals'>Approvals</NavData>
      </div>
    </aside>
  );
}

type NavDataProps = {
  children: React.ReactNode;
  href: string;
};

function NavData({ children, href }: NavDataProps) {
  return (
    <Link
      href={href}
      className={`py-2 pl-16 bg-offWhite transition delay-75 ease-in ${cn(
        usePathname() === href &&
          'font-semibold border-l-4 border-blue-800 brightness-90',
        usePathname() !== href && 'hover:brightness-95 hover:font-medium'
      )}`}
    >
      {children}
    </Link>
  );
}
