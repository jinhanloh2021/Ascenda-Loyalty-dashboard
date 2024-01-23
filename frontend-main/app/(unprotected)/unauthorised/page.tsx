import React from "react";
import Link from "next/link";

type Props = {};

export default async function Unauthorised({}: Props) {
  return (
    <>
      <div className="flex flex-col items-center justify-center">
        <h1 className="text-2xl font-bold">
          You are unauthorised to visit this page
        </h1>
        <h3 className="mt-4 mb-4">
          Please go back to the{" "}
          <Link href="/" className="text-blue-500 hover:underline">
            home page
          </Link>
        </h3>
      </div>
    </>
  );
}
