'use client';
import { Button, buttonVariants } from '@/components/ui/button';
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select';
import { Input } from '@/components/ui/input';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import * as z from 'zod';
import { Form, FormControl, FormField, FormItem, FormLabel } from '../ui/form';
import { cn } from '@/lib/utils';
import { updateUserById } from '@/api/fetchUsers';
import { User } from '@/interfaces/User';
import RoleSelection from './roleSelection';

const userSchema = z.object({
  name: z.string(),
  email: z.string().email().trim().toLowerCase(),
  role: z.string().trim(),
});

type UserSchema = z.infer<typeof userSchema>;

type props = {
  id: string;
  user: User;
  roles: string[];
  jwt: string | undefined;
};

export default function UpdateUserForm({ id, user, roles, jwt }: props) {
  const form = useForm<UserSchema>({
    resolver: zodResolver(userSchema),
    defaultValues: {
      name: user.name,
      email: user.email,
      role: user.role,
    },
    mode: 'onTouched',
  });

  const onSubmit = (data: UserSchema) => {
    updateUserById(id, data, jwt);
  };

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className='grid gap-4 mb-8'>
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
              <FormLabel htmlFor='email' className='text-right'>
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
                    <SelectValue placeholder='Select Role (optional)' />
                  </SelectTrigger>
                </FormControl>
                <SelectContent>
                  <RoleSelection roles={roles} />
                </SelectContent>
              </Select>
            </FormItem>
          )}
        />
        <div className='flex flex-col-reverse sm:flex-row sm:justify-end sm:space-x-2'>
          <Button
            disabled={
              !(
                form.formState.dirtyFields.email ||
                form.formState.dirtyFields.name ||
                form.formState.dirtyFields.role
              )
            }
            type='submit'
            className={cn(
              buttonVariants({ variant: 'default' }),
              'bg-blue-800'
            )}
          >
            Update User
          </Button>
        </div>
      </form>
    </Form>
  );
}
