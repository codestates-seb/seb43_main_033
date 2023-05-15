import React from "react";

type BoxProps = {
  children: React.ReactNode;
};

export default function StaffSelect({ children }: BoxProps) {
  return (
    <div className="bg-gray-200 flex justify-between w-60 pr-2">
    <div className="pt-1 pl-2 py-2">{children}</div>
    <input 
      type="checkbox" 
    />
    </div>
  );
}