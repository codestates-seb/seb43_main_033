import React, { ChangeEvent } from "react";

type StaffInputProps = {
  label: string;
  value: number | string | string[];
  onChange: (e: ChangeEvent<HTMLInputElement> | ChangeEvent<HTMLSelectElement>) => void;
  name: string;
  options?: string[];
};

export default function StaffInput({ label, value, onChange, name, options = [] }: StaffInputProps) {
  return (
    <div className="ml-10">
      <div className="mt-2 ml-5 mb-2 pl-3 font-bold">{label}</div>
      {name === "roles" ? (
        <select
          className="h-8 w-32 ml-5 mb-2 pl-3 border-b border-gray-300 focus:outline-none hover:outline-none"
          name={name}
          value={value}
          onChange={onChange}
        >
          <option value="">선택하세요</option>
          {options.map((option) => (
            <option key={option} value={option}>
              {option}
            </option>
          ))}
        </select>
      ) : (
        <input
          className="h-8 w-32 ml-5 mb-2 pl-3 border-b border-gray-300 focus:outline-none hover:outline-none"
          type="text"
          value={value}
          onChange={onChange}
          name={name}
        />
      )}
    </div>
  );
}
