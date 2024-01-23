import { LoginForm } from "@/components/loginForm/loginForm";
import React from "react";

type Props = {};

export default async function Login({}: Props) {

  return (
    <>
      <div className="flex flex-col items-center justify-center">
        <h1 className="text-2xl font-bold">Login</h1>
        <h3 className="mt-4 mb-4">Welcome! Please key in your email and password!</h3>
        <LoginForm />
      </div>
    </>
  );
}
