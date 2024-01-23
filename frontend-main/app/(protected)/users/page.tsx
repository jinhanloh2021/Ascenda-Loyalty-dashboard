import { getPointsByUserId } from '@/api/fetchPointsBalance';
import { getAllUsers } from '@/api/fetchUsers';
import EnrolUserDialogue from '@/components/userForms/enrolUserDialogue';
import { userColumns } from '@/components/userTable/userColumns';
import UserTable from '@/components/userTable/userTable';
import { PointsBalance } from '@/interfaces/PointsBalance';
import React from 'react';

type Props = {};

export default async function Users({}: Props) {
  const allUsers = await getAllUsers();
  const userPointsPromises = allUsers.map(async (user) => {
    const points = await getPointsByUserId(user.userId);
    const totalPoints = points.reduce(
      (acc: number, point: PointsBalance) => acc + point.balance,
      0
    );
    return {
      userId: user.userId,
      name: user.name,
      email: user.email,
      totalPoints: totalPoints,
      role: user.role,
    };
  });

  const userPoints = await Promise.all(userPointsPromises);
  return (
    <>
      <div className='flex flex-row justify-between items-center'>
        <h1 className='text-2xl font-bold'>Users</h1>
        <EnrolUserDialogue />
      </div>
      <UserTable columns={userColumns} data={userPoints} />
      {/* Data fetching can be done within table. Keep this server component */}
      {/* Also fetch points in table and aggregate for each user */}
    </>
  );
}
