import { SignUpForm } from '@/components/signUpForm/signUpForm';
import Link from 'next/link';
import React from 'react';

type Props = {};

export default async function Sign({}: Props) {
  return (
    <>
      <div className='flex flex-col items-center justify-center'>
        <h1 className='text-2xl font-bold'>Sign Up</h1>
        <h3 className='mt-4 mb-4'>
          Welcome! Please sign up with a valid email address
        </h3>
        <SignUpForm />
      </div>
    </>
  );
}
