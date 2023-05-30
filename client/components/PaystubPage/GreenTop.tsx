import React from "react";

type BoxProps = {
  children: React.ReactNode;
};

export default function Greenheader({ children }: BoxProps) {
  return (
    <div className="bg-green-100 w-full p-2 m-5 flex justify-between">
      {children}
    </div>
  );
}
