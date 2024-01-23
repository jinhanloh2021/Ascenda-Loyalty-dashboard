'use client';
import { Button, buttonVariants } from '@/components/ui/button';
import {
  Checkbox,
  CheckboxTrigger,
  CheckboxIndicator,
} from '@/components/ui/checkbox';

import { Input } from '@/components/ui/input';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import * as z from 'zod';
import { Form, FormControl, FormField, FormItem, FormLabel } from '../ui/form';
import { cn } from '@/lib/utils';
import { updateRoleById } from '@/api/fetchRoles';

import { Role } from '@/interfaces/Role';

import * as React from 'react';

const permissionsSchema = z.object({
  create: z.boolean(),
  read: z.boolean(),
  update: z.boolean(),
  delete: z.boolean(),
});

const roleSchema = z.object({
  RoleName: z.string(),
  UserStorage: permissionsSchema,
  PointLedger: permissionsSchema,
  Logs: permissionsSchema,
  Role: permissionsSchema,
});

type RoleSchema = z.infer<typeof roleSchema>;

type props = {
  id: string;
  role: Role;
};

export default function UpdateRoleForm({ id, role }: props) {
  const form = useForm<RoleSchema>({
    resolver: zodResolver(roleSchema),
    defaultValues: {
      RoleName: role.RoleName,
      UserStorage: role.UserStorage,
      PointLedger: role.PointLedger,
      Logs: role.Logs,
      Role: role.Role,
    },
    mode: 'onTouched',
  });

  const onSubmit = async (data: RoleSchema) => {
    console.log(data);
    await updateRoleById(id, data);
  };

  const [userCreateChecked, setUserCreateChecked] = React.useState(false);
  const [userReadChecked, setUserReadChecked] = React.useState(false);
  const [userUpdateChecked, setUserUpdateChecked] = React.useState(false);
  const [userDeleteChecked, setUserDeleteChecked] = React.useState(false);

  const [pointCreateChecked, setPointCreateChecked] = React.useState(false);
  const [pointReadChecked, setPointReadChecked] = React.useState(false);
  const [pointUpdateChecked, setPointUpdateChecked] = React.useState(false);
  const [pointDeleteChecked, setPointDeleteChecked] = React.useState(false);

  const [roleCreateChecked, setRoleCreateChecked] = React.useState(false);
  const [roleReadChecked, setRoleReadChecked] = React.useState(false);
  const [roleUpdateChecked, setRoleUpdateChecked] = React.useState(false);
  const [roleDeleteChecked, setRoleDeleteChecked] = React.useState(false);

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className='grid gap-4 py-4'>
        <FormField
          control={form.control}
          name='RoleName'
          render={({ field }) => (
            <FormItem className='grid grid-cols-4 items-center gap-4'>
              <FormLabel htmlFor='name' className='text-right'>
                Role Name
              </FormLabel>
              <FormControl>
                <Input id='name' className='col-span-3' {...field} />
              </FormControl>
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name='UserStorage'
          render={({ field }) => (
            <FormItem className='grid grid-cols-5 items-center gap-4'>
              <FormLabel htmlFor='roles' className='text-right'>
                User Storage
              </FormLabel>
              <Checkbox
                checked={userCreateChecked}
                onCheckedChange={() => setUserCreateChecked(!userCreateChecked)}
              >
                <CheckboxTrigger>C</CheckboxTrigger>
                <CheckboxIndicator />
              </Checkbox>
              <Checkbox
                checked={userReadChecked}
                onCheckedChange={() => setUserReadChecked(!userReadChecked)}
              >
                <CheckboxTrigger>R</CheckboxTrigger>
                <CheckboxIndicator />
              </Checkbox>
              <Checkbox
                checked={userUpdateChecked}
                onCheckedChange={() => setUserUpdateChecked(!userUpdateChecked)}
              >
                <CheckboxTrigger>U</CheckboxTrigger>
                <CheckboxIndicator />
              </Checkbox>
              <Checkbox
                checked={userDeleteChecked}
                onCheckedChange={() => setUserDeleteChecked(!userDeleteChecked)}
              >
                <CheckboxTrigger>D</CheckboxTrigger>
                <CheckboxIndicator />
              </Checkbox>
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name='PointLedger'
          render={({ field }) => (
            <FormItem className='grid grid-cols-5 items-center gap-4'>
              <FormLabel htmlFor='roles' className='text-right'>
                Point Ledger
              </FormLabel>
              <Checkbox
                checked={pointCreateChecked}
                onCheckedChange={() =>
                  setPointCreateChecked(!pointCreateChecked)
                }
              >
                <CheckboxTrigger>C</CheckboxTrigger>
                <CheckboxIndicator />
              </Checkbox>
              <Checkbox
                checked={pointReadChecked}
                onCheckedChange={() => setPointReadChecked(!pointReadChecked)}
              >
                <CheckboxTrigger>R</CheckboxTrigger>
                <CheckboxIndicator />
              </Checkbox>
              <Checkbox
                checked={pointUpdateChecked}
                onCheckedChange={() =>
                  setPointUpdateChecked(!pointUpdateChecked)
                }
              >
                <CheckboxTrigger>U</CheckboxTrigger>
                <CheckboxIndicator />
              </Checkbox>
              <Checkbox
                checked={pointDeleteChecked}
                onCheckedChange={() =>
                  setPointDeleteChecked(!pointDeleteChecked)
                }
              >
                <CheckboxTrigger>D</CheckboxTrigger>
                <CheckboxIndicator />
              </Checkbox>
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name='Role'
          render={({ field }) => (
            <FormItem className='grid grid-cols-5 items-center gap-4'>
              <FormLabel htmlFor='roles' className='text-right'>
                Role
              </FormLabel>
              <Checkbox
                checked={roleCreateChecked}
                onCheckedChange={() => setRoleCreateChecked(!roleCreateChecked)}
              >
                <CheckboxTrigger>C</CheckboxTrigger>
                <CheckboxIndicator />
              </Checkbox>
              <Checkbox
                checked={roleReadChecked}
                onCheckedChange={() => setRoleReadChecked(!roleReadChecked)}
              >
                <CheckboxTrigger>R</CheckboxTrigger>
                <CheckboxIndicator />
              </Checkbox>
              <Checkbox
                checked={roleUpdateChecked}
                onCheckedChange={() => setRoleUpdateChecked(!roleUpdateChecked)}
              >
                <CheckboxTrigger>U</CheckboxTrigger>
                <CheckboxIndicator />
              </Checkbox>
              <Checkbox
                checked={roleDeleteChecked}
                onCheckedChange={() => setRoleDeleteChecked(!roleDeleteChecked)}
              >
                <CheckboxTrigger>D</CheckboxTrigger>
                <CheckboxIndicator />
              </Checkbox>
            </FormItem>
          )}
        />
        <div className='flex flex-col-reverse sm:flex-row sm:justify-end sm:space-x-2'>
          <Button
            disabled={
              !(
                form.formState.dirtyFields.UserStorage ||
                form.formState.dirtyFields.RoleName ||
                form.formState.dirtyFields.PointLedger ||
                form.formState.dirtyFields.Role
              )
            }
            type='submit'
            className={cn(
              buttonVariants({ variant: 'default' }),
              'bg-blue-800'
            )}
          >
            Update Role
          </Button>
        </div>
      </form>
    </Form>
  );
}
