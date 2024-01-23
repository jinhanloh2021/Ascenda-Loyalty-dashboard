import { RoleSchema } from "@/components/roleForms/enrolRoleForm";
import { Role, RoleRes } from "@/interfaces/Role";
import { getEncodedJwt } from "@/lib/utils";

const URL = "https://www.proxy.itsag2t3.com:8001"; //should be .env

const authFixture = async () => {
  const jwt = await getEncodedJwt();
  const headers = new Headers();
  headers.append("Authorization", `Bearer ${jwt}`);

  return { jwt, headers };
};

export const getAllRoles = async (): Promise<RoleRes[]> => {
  const { headers } = await authFixture();

  const res = await fetch(`${URL}/v1/app/role`, {
    method: "GET",
    headers: headers,
  });

  return await res.json();
};

export const getRoleById = async (id: string) => {
  const { headers } = await authFixture();

  const res = await fetch(`${URL}/v1/app/role/${id}`, {
    method: 'GET',
    headers: headers,
  });

  return await res.json();
};

export const addRole = async (data: RoleSchema) => {
  try {
    const res = await fetch(`${URL}/v1/app/role`, {
      method: 'POST',
      body: JSON.stringify(data),
    });
    return await res.json();
  } catch (e) {
    console.log(e);
    return e;
  }
};

export const updateRoleById = async (id: string, data: RoleSchema) => {
  // try {
  //   const res = await fetch('', {
  //     method: 'PUT',
  //     body: JSON.stringify(data),
  //   });
  //   return await res.json();
  // } catch (e) {
  //   console.log(e);
  //   return e;
  // }
};
