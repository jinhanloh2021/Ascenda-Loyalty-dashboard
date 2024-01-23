import { getPointsByUserId } from '@/api/fetchPointsBalance';
import { getAllRoles } from '@/api/fetchRoles';
import { getUserById } from '@/api/fetchUsers';
import UpdatePointsForm from '@/components/pointsForms/UpdatePointsForm';
import { Separator } from '@/components/ui/separator';
import UpdateUserForm from '@/components/userForms/updateUserForm';
import { PointsBalance } from '@/interfaces/PointsBalance';
import { User } from '@/interfaces/User';
import { getEncodedJwt } from '@/lib/utils';
import React from 'react';

type Props = {
  params: {
    id: string;
  };
};

export default async function UserIdPage({ params }: Props) {
  const user: User = await getUserById(params.id);
  const userBalances: PointsBalance[] = await getPointsByUserId(params.id);
  const allRoles = await getAllRoles();
  const allRoleNames = allRoles.map((e) => {
    return e.RoleName;
  });
  const eJwt = await getEncodedJwt();

  return (
    <>
      <div className='w-[30rem]'>
        <div className='mb-4 grid grid-cols-4 gap-x-4'>
          <div></div>
          <h1 className='text-2xl font-bold mb-2 col-span-3'>
            User Information
          </h1>
          <div></div>
          <h3 className='text-sm font-normal col-span-3'>
            {user.name} <span className='text-slate-400'>({user.userId})</span>
          </h3>
        </div>
        <UpdateUserForm
          id={params.id}
          user={user}
          roles={allRoleNames}
          jwt={eJwt}
        />
        <Separator />
        <div className='mb-4 grid grid-cols-4 gap-x-4'>
          <div></div>
          <h1 className='mt-8 text-2xl font-bold col-span-3'>
            Points Information
          </h1>
        </div>
        {userBalances.map((e, i) => (
          <UpdatePointsForm key={i} pointsBalance={e} jwt={eJwt} />
        ))}
      </div>
    </>
  );
}
