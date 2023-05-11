import React from "react";

type BoxProps = {
  children: React.ReactNode;
};

export default function ListBox({ children }: BoxProps) {
  return (
    <div className="bg-white p-2 m-5 flex justify-between">
      {children}
    </div>
  );
}