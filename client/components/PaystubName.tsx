import React from "react";

type BoxProps = {
  children: React.ReactNode;
};

export default function PaystubName({ children }: BoxProps) {
  return (
    <div className="bg-gray-200 flex justify-center w-60 pr-2 mb-10">
    <div className="pl-2 py-5">{children}</div>
    </div>
  );
}