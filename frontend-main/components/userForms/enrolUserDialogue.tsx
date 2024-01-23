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
import EnrolUserForm from './enrolUserForm';
import { getAllRoles } from '@/api/fetchRoles';
import { getEncodedJwt } from '@/lib/utils';

type Props = {};

export default async function EnrolUserDialogue({}: Props) {
  const allRoles = await getAllRoles();
  const allRoleNames = allRoles.map((e) => {
    return e.RoleName;
  });
  const eJwt = await getEncodedJwt();

  return (
    <Dialog>
      <DialogTrigger asChild>
        <Button variant='default' className='bg-blue-800'>
          Enrol User
        </Button>
      </DialogTrigger>
      <DialogContent className='sm:max-w-[425px]'>
        <DialogHeader>
          <DialogTitle>User Details</DialogTitle>
          <DialogDescription>Input the new user details here</DialogDescription>
        </DialogHeader>
        <EnrolUserForm roles={allRoleNames} jwt={eJwt} />
      </DialogContent>
    </Dialog>
  );
}
