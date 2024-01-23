import { getRoleById } from "@/api/fetchRoles";
import { Separator } from "@/components/ui/separator";
import UpdateRoleForm from "@/components/roleForms/updateRoleForm";
import { Role } from "@/interfaces/Role";
import React from "react";
import { PermissionsSchema } from "@/components/roleForms/enrolRoleForm";

type Props = {
  params: {
    id: string;
  };
};

export default async function RoleIdPage({ params }: Props) {
  interface RenderPermissionsProps {
    data: PermissionsSchema; // Replace PermissionsSchema with the actual type
  }
  const role: Role = await getRoleById(params.id);
  const RenderPermissions: React.FC<RenderPermissionsProps> = ({ data }) => {
    let result = "";

    if (data.create) {
      result += "C";
    }

    if (data.read) {
      result += "R";
    }

    if (data.update) {
      result += "U";
    }

    if (data.delete) {
      result += "D";
    }

    if (result === "") {
      result += "-";
    }
    return <span className="text-sm mb-4 col-span-3">{result}</span>;
  };

  return (
    <>
      <div className="w-[30rem]">
        <div className="mb-4 grid grid-cols-4 gap-x-4">
          <div></div>
          <h1 className="text-2xl font-bold mb-2 col-span-3">
            Role Information
          </h1>
        </div>
        <div className="mb-4 grid grid-cols-4 gap-x-4">
          <div></div>
          <h2 className="text-base font-semibold mb-2 col-span-3">Role Name</h2>
          <div></div>
          <span className="text-sm mb-4 col-span-3">{role.RoleName}</span>
          <div></div>
          <h2 className="text-base font-semibold mb-2 col-span-3">Role ID</h2>
          <div></div>
          <span className="text-sm mb-4 col-span-3">{role.RoleID}</span>
          <div></div>
          <h2 className="text-base font-semibold mb-2 col-span-3">
            User Storage Permissions
          </h2>
          <div></div>
          <RenderPermissions data={role.UserStorage} />
          <div></div>
          <h2 className="text-base font-semibold mb-2 col-span-3">
            Point Ledger Permissions
          </h2>
          <div></div>
          <RenderPermissions data={role.PointLedger} />
          <div></div>
          <h2 className="text-base font-semibold mb-2 col-span-3">
            Logs Permissions
          </h2>
          <div></div>
          <RenderPermissions data={role.Logs} />
          <div></div>
          <h2 className="text-base font-semibold mb-2 col-span-3">
            Role Permissions
          </h2>
          <div></div>
          <RenderPermissions data={role.Role} />
          <div></div>
        </div>
        <Separator />
        <UpdateRoleForm id={params.id} role={role} />
      </div>
    </>
  );
}
