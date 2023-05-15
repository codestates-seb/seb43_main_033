import React from "react";

type BoxProps = {
  children: React.ReactNode;
};

export default function Bigsquare({ children }: BoxProps) {
  return (
    <div className="bg-gray-200 rounded-md w-full h-full mx-10">
      {children}
    </div>
  );
}
