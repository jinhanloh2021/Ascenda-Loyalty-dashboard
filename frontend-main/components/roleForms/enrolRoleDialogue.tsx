import React from 'react';
import { Button } from '@/components/ui/button';
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from '@/components/ui/dialog';
import EnrolRoleForm from './enrolRoleForm';
import { getEncodedJwt } from '@/lib/utils';

type Props = {};

export default async function EnrolRoleDialogue({}: Props) {
  const eJwt = await getEncodedJwt();
  return (
    <Dialog>
      <DialogTrigger asChild>
        <Button variant='default' className='bg-blue-800'>
          Create Role
        </Button>
      </DialogTrigger>
      <DialogContent className='sm:max-w-[425px]'>
        <DialogHeader>
          <DialogTitle>Role Details</DialogTitle>
          <DialogDescription>Input the new role details here</DialogDescription>
        </DialogHeader>
        <EnrolRoleForm jwt={eJwt} />
      </DialogContent>
    </Dialog>
  );
}
