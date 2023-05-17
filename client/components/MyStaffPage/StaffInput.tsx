import React, { ChangeEvent } from "react";

type StaffInputdProps = {
    label: string;
    value: number | string;
    onChange: (e: ChangeEvent<HTMLInputElement>) => void;
    name: string;
  };

export default function StaffInput({ label, value, onChange, name }:StaffInputdProps){
  return (
    <div className="ml-10">
      <div className="mt-2 ml-5 mb-2 pl-3 font-bold">{label}</div>
      <input
        className="h-5 w-20 ml-5 mb-2 pl-3 border-b border-gray-300 focus:outline-none hover:outline-none"
        type="text"
        value={value}
        onChange={onChange}
        name={name}
      />
    </div>
  );
};
