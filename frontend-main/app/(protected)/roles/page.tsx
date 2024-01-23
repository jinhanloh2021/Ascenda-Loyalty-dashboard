import { getAllRoles } from '@/api/fetchRoles';
import EnrolRoleDialogue from '@/components/roleForms/enrolRoleDialogue';
import { roleColumns } from '@/components/roleTable/roleColumns';
import RoleTable from '@/components/roleTable/roleTable';
import React from 'react';

type Props = {};

export default async function Roles({}: Props) {
  const allRoles = await getAllRoles();
  const formattedRoles = allRoles.map((role) => {
    const { roleAuthorities, ...rest } = role;
    return rest;
  });
  return (
    <>
      <div className='flex flex-row justify-between items-center'>
        <h1 className='text-2xl font-bold'>Roles</h1>
        <EnrolRoleDialogue />
      </div>
      <RoleTable columns={roleColumns} data={formattedRoles} />

      {/* Data fetching can be done within table. Keep this server component */}
      {/* Also fetch points in table and aggregate for each user */}
    </>
  );
}
