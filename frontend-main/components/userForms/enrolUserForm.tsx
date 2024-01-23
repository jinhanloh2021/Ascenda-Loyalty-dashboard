'use client';
import { buttonVariants } from '@/components/ui/button';
import { DialogClose, DialogFooter } from '@/components/ui/dialog';
import {
  Select,
  SelectContent,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select';
import { Input } from '@/components/ui/input';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import * as z from 'zod';
import { Form, FormControl, FormField, FormItem, FormLabel } from '../ui/form';
import { cn } from '@/lib/utils';
import { addUser } from '@/api/fetchUsers';
import RoleSelection from './roleSelection';

const userSchema = z.object({
  name: z.string(),
  email: z.string().email().trim().toLowerCase(),
  role: z.string().trim(),
});

export type UserSchema = z.infer<typeof userSchema>;

type props = {
  roles: string[];
  jwt: string | undefined;
};

export default function EnrolUserForm({ roles, jwt }: props) {
  const form = useForm<UserSchema>({
    resolver: zodResolver(userSchema),
    defaultValues: {
      name: '',
      email: '',
      role: 'Default',
    },
    mode: 'onTouched',
  });

  const onSubmit = (data: UserSchema) => {
    addUser(data, jwt);
  };

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className='grid gap-4 py-4'>
        <FormField
          control={form.control}
          name='name'
          render={({ field }) => (
            <FormItem className='grid grid-cols-4 items-center gap-4'>
              <FormLabel htmlFor='name' className='text-right'>
                Name
              </FormLabel>
              <FormControl>
                <Input id='name' className='col-span-3' {...field} />
              </FormControl>
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name='email'
          render={({ field }) => (
            <FormItem className='grid grid-cols-4 items-center gap-4'>
              <FormLabel htmlFor='name' className='text-right'>
                Email
              </FormLabel>
              <FormControl>
                <Input id='email' className='col-span-3' {...field} />
              </FormControl>
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name='role'
          render={({ field }) => (
            <FormItem className='grid grid-cols-4 items-center gap-4'>
              <FormLabel htmlFor='role' className='text-right'>
                Role
              </FormLabel>
              <Select onValueChange={field.onChange} defaultValue={field.value}>
                <FormControl>
                  <SelectTrigger className='col-span-3'>
                    <SelectValue placeholder='Select Role' />
                  </SelectTrigger>
                </FormControl>
                <SelectContent>
                  <RoleSelection roles={roles} />
                </SelectContent>
              </Select>
            </FormItem>
          )}
        />
        <DialogFooter>
          <DialogClose
            disabled={!form.formState.isValid}
            type='submit'
            className={cn(
              buttonVariants({ variant: 'default' }),
              'bg-blue-800'
            )}
          >
            Submit
          </DialogClose>
        </DialogFooter>
      </form>
    </Form>
  );
}
