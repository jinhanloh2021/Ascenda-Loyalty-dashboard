import { UserSchema } from '@/components/userForms/enrolUserForm';

export type User = UserSchema & {
  userId: string;
};

export type UserRes = User & {
  password: string;
  permissions: any;
  enabled: boolean;
  username: string;
  authorities: [{ authority: string }];
  accountNonExpired: boolean;
  accountNonLocked: boolean;
  credentialsNonExpired: boolean;
};

export type AddUserRes = {
  name: string;
  email: string;
  password: string;
};
