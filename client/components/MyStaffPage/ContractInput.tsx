import React, { ChangeEvent } from "react";

type ContractInputProps = {
  label: string;
  type: "text";
  name: string;
  value: number | string;
  onChange: (e: ChangeEvent<HTMLInputElement>) => void;
};

export default function ContractInput({
  label,
  type,
  name,
  value,
  onChange,
}: ContractInputProps) {
  return (
    <div>
      <div className="ml-7 pt-2 pb-2 font-bold">{label}</div>
      <input
        className="w-40 ml-7 pb-2 border-b border-gray-300 focus:outline-none hover:outline-none"
        type={type}
        name={name}
        value={value}
        onChange={onChange}
      />
    </div>
  );
}
