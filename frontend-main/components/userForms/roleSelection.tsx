import React from 'react';
import { SelectItem } from '../ui/select';

type Props = {
  roles: string[];
};

export default function roleSelection({ roles }: Props) {
  return (
    <>
      {roles.map((role, i) => (
        <SelectItem key={i} value={role}>
          {role}
        </SelectItem>
      ))}
    </>
  );
}
