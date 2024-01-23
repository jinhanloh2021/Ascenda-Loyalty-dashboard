"use client";
import { Role } from "@/interfaces/Role";
import { ColumnDef } from "@tanstack/react-table";
import { buttonVariants } from "@/components/ui/button";
import { ArrowTopRightIcon } from "@radix-ui/react-icons";
import Link from "next/link";
import { PermissionsSchema } from "../roleForms/enrolRoleForm";

interface RenderPermissionsProps {
  data: PermissionsSchema; // Replace PermissionsSchema with the actual type
}

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

  if (result === ""){
    result += "-"
  }
  return <span className="text-sm mb-4 col-span-3">{result}</span>;
};

export default RenderPermissions;
export const roleColumns: ColumnDef<Role>[] = [
  {
    accessorKey: "RoleID",
    header: "Role ID",
  },
  {
    accessorKey: "RoleName",
    header: "Role Name",
  },
  {
    accessorKey: "UserStorage",
    header: "User Storage",
    cell: ({ row }) => {
      const value: PermissionsSchema = row.getValue(
        "UserStorage"
      ) as PermissionsSchema;
      return <RenderPermissions data={value} />;
    },
  },
  {
    accessorKey: "PointLedger",
    header: "Point Ledger",
    cell: ({ row }) => {
      const value: PermissionsSchema = row.getValue(
        "PointLedger"
      ) as PermissionsSchema;
      return <RenderPermissions data={value} />;
    },
  },
  {
    accessorKey: "Logs",
    header: "Logs",
    cell: ({ row }) => {
      const value: PermissionsSchema = row.getValue(
        "Logs"
      ) as PermissionsSchema;
      return <RenderPermissions data={value} />;
    },
  },
  {
    accessorKey: "Role",
    header: "Role",
    cell: ({ row }) => {
      const value: PermissionsSchema = row.getValue(
        "Role"
      ) as PermissionsSchema;
      return <RenderPermissions data={value} />;
    },
  },
  {
    id: "actions",
    cell: ({ row }) => (
      <Link
        className={buttonVariants({ variant: "ghost", size: "icon" })}
        href={`/roles/${row.getValue("RoleID")}`}
      >
        <ArrowTopRightIcon />
      </Link>
    ),
  },
];
