import { RoleSchema } from "@/components/roleForms/enrolRoleForm";

export type Role = RoleSchema & {
  RoleID: string;
};

export type RoleRes = Role & {
  roleAuthorities: [{ authority: string }];
};
