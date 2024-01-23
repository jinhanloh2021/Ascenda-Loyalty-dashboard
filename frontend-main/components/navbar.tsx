"use client";
import Link from "next/link";
import React from "react";
import { Button } from "@/components/ui/button";
import { signOut } from "next-auth/react";

type Props = {};

export default function Navbar({}: Props) {
  return (
    <nav className="bg-blue-800 py-4 px-16 text-slate-50 font-semibold flex flex-row justify-between items-center text-lg sticky top-0 z-50 col-span-12 row-span-2">
      <Link href="/" className="text-lg">
        Ascenda Admin Panel
      </Link>
      <Button
        variant={"ghost"}
        className="text-lg"
        onClick={async () => await signOut()}
      >
        Logout
      </Button>
    </nav>
  );
}
