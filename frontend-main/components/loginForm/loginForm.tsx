'use client';

import * as z from 'zod';
import { zodResolver } from '@hookform/resolvers/zod';
import { useForm } from 'react-hook-form';
import { Button } from '@/components/ui/button';
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from '@/components/ui/form';
import { Input } from '@/components/ui/input';
import Link from 'next/link';
import { signIn } from 'next-auth/react';

const loginSchema = z.object({
  email: z
    .string()
    .email({
      message: 'Please enter a valid email address.',
    })
    .trim()
    .toLowerCase(),
  password: z.string(),
});

export function LoginForm() {
  const form = useForm<z.infer<typeof loginSchema>>({
    resolver: zodResolver(loginSchema),
    defaultValues: {
      email: '',
      password: '',
    },
  });

  function onSubmit(values: z.infer<typeof loginSchema>) {
    // For a login page, you would typically send the data to a server for authentication.
    // Example: Call an API endpoint to verify the user's credentials.
    // For demonstration purposes, we'll just log the values.
    signIn('credentials', {
      email: values.email,
      password: values.password,
      callbackUrl: process.env.NEXTAUTH_URL ?? 'http://localhost:3000',
    });
  }

  return (
    <div className='w-full'>
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit)} className='space-y-8'>
          <FormField
            control={form.control}
            name='email'
            render={({ field }) => (
              <FormItem>
                <FormLabel>Email</FormLabel>
                <FormControl>
                  <Input placeholder='example@email.com' {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name='password'
            render={({ field }) => (
              <FormItem>
                <FormLabel>Password</FormLabel>
                <FormControl>
                  <Input type='password' {...field} />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />

          <div className='text-sm text-center'>
            Not signed up with us yet?{' '}
            <Link href='/signup' className='text-blue-500 hover:underline'>
              Sign up
            </Link>{' '}
            here
          </div>

          <div className='flex justify-end'>
            <Button
              type='submit'
              className='bg-blue-800 text-white hover:bg-blue-900'
            >
              Login
            </Button>
          </div>
        </form>
      </Form>
    </div>
  );
}
