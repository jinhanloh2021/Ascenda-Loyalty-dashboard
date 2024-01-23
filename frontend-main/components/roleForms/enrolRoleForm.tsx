'use client';
import { buttonVariants } from '@/components/ui/button';
import { DialogClose, DialogFooter } from '@/components/ui/dialog';
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
import { addRole } from '@/api/fetchRoles';

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

export type RoleSchema = z.infer<typeof roleSchema>;
export type PermissionsSchema = z.infer<typeof permissionsSchema>;

type props = {
  jwt: string | undefined;
};

export default function EnrolRoleForm({ jwt }: props) {
  const form = useForm<RoleSchema>({
    resolver: zodResolver(roleSchema),
    defaultValues: {
      RoleName: '',
      UserStorage: {
        create: false,
        read: false,
        update: false,
        delete: false,
      },
      PointLedger: {
        create: false,
        read: false,
        update: false,
        delete: false,
      },
      Logs: {
        create: false,
        read: false,
        update: false,
        delete: false,
      },
      Role: {
        create: false,
        read: false,
        update: false,
        delete: false,
      },
    },
    mode: 'onTouched',
  });

  const onSubmit = async (data: RoleSchema) => {
    console.log(data);
    // await addRole(data);
  };

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className='grid gap-4 py-4'>
        <FormField
          control={form.control}
          name='RoleName'
          render={({ field }) => (
            <FormItem className='grid grid-cols-4 justify-items-center items-center gap-4'>
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
              <FormLabel htmlFor='userStorage' className='text-right'>
                User Storage
              </FormLabel>
              <Checkbox
                checked={field.value.create}
                onChange={(e) =>
                  form.setValue(
                    'UserStorage.create',
                    (e.target as HTMLInputElement).checked
                  )
                }
              >
                <CheckboxTrigger>C</CheckboxTrigger>
                <CheckboxIndicator />
              </Checkbox>
              <Checkbox
                checked={field.value.read}
                onChange={(e) =>
                  form.setValue(
                    'UserStorage.read',
                    (e.target as HTMLInputElement).checked
                  )
                }
              >
                <CheckboxTrigger>R</CheckboxTrigger>
                <CheckboxIndicator />
              </Checkbox>
              <Checkbox
                checked={field.value.update}
                onChange={(e) =>
                  form.setValue(
                    'UserStorage.update',
                    (e.target as HTMLInputElement).checked
                  )
                }
              >
                <CheckboxTrigger>U</CheckboxTrigger>
                <CheckboxIndicator />
              </Checkbox>
              <Checkbox
                checked={field.value.delete}
                onChange={(e) =>
                  form.setValue(
                    'UserStorage.delete',
                    (e.target as HTMLInputElement).checked
                  )
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
          name='PointLedger'
          render={({ field }) => (
            <FormItem className='grid grid-cols-5 items-center gap-4'>
              <FormLabel htmlFor='pointLedger' className='text-right'>
                Points Ledger
              </FormLabel>
              <Checkbox
                checked={field.value.create}
                onChange={(e) =>
                  form.setValue(
                    'PointLedger.create',
                    (e.target as HTMLInputElement).checked
                  )
                }
              >
                <CheckboxTrigger>C</CheckboxTrigger>
                <CheckboxIndicator />
              </Checkbox>
              <Checkbox
                checked={field.value.read}
                onChange={(e) =>
                  form.setValue(
                    'PointLedger.read',
                    (e.target as HTMLInputElement).checked
                  )
                }
              >
                <CheckboxTrigger>R</CheckboxTrigger>
                <CheckboxIndicator />
              </Checkbox>
              <Checkbox
                checked={field.value.update}
                onChange={(e) =>
                  form.setValue(
                    'PointLedger.update',
                    (e.target as HTMLInputElement).checked
                  )
                }
              >
                <CheckboxTrigger>U</CheckboxTrigger>
                <CheckboxIndicator />
              </Checkbox>
              <Checkbox
                checked={field.value.delete}
                onChange={(e) =>
                  form.setValue(
                    'PointLedger.delete',
                    (e.target as HTMLInputElement).checked
                  )
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
          name='Logs'
          render={({ field }) => (
            <FormItem className='grid grid-cols-5 items-center gap-4'>
              <FormLabel htmlFor='userStorage' className='text-right'>
                Logs
              </FormLabel>
              <div></div>
              <Checkbox
                checked={field.value.read}
                onChange={(e) =>
                  form.setValue(
                    'Logs.read',
                    (e.target as HTMLInputElement).checked
                  )
                }
              >
                <CheckboxTrigger>R</CheckboxTrigger>
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
              <FormLabel htmlFor='role' className='text-right'>
                Role
              </FormLabel>
              <Checkbox
                checked={field.value.create}
                onChange={(e) =>
                  form.setValue(
                    'Role.create',
                    (e.target as HTMLInputElement).checked
                  )
                }
              >
                <CheckboxTrigger>C</CheckboxTrigger>
                <CheckboxIndicator />
              </Checkbox>
              <Checkbox
                checked={field.value.read}
                onChange={(e) =>
                  form.setValue(
                    'Role.read',
                    (e.target as HTMLInputElement).checked
                  )
                }
              >
                <CheckboxTrigger>R</CheckboxTrigger>
                <CheckboxIndicator />
              </Checkbox>
              <Checkbox
                checked={field.value.update}
                onChange={(e) =>
                  form.setValue(
                    'Role.update',
                    (e.target as HTMLInputElement).checked
                  )
                }
              >
                <CheckboxTrigger>U</CheckboxTrigger>
                <CheckboxIndicator />
              </Checkbox>
              <Checkbox
                checked={field.value.delete}
                onChange={(e) =>
                  form.setValue(
                    'Role.delete',
                    (e.target as HTMLInputElement).checked
                  )
                }
              >
                <CheckboxTrigger>D</CheckboxTrigger>
                <CheckboxIndicator />
              </Checkbox>
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
