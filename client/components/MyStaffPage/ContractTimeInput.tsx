import React, { ChangeEvent } from "react";

type ContractTimeInputProps = {
  label: string;
  type: "time" | "date";
  startTime: string;
  finishTime: string;
  onChange: (e: ChangeEvent<HTMLInputElement>) => void;
};

export default function ContractTimeInput({ 
    label, type,startTime, finishTime, onChange }:ContractTimeInputProps) {
    return (
      <div>
        <div className="mt-2 mb-2 ml-8 font-bold">{label}</div>
        <div className="flex items-center ml-8">
          <input
            className="flex-1 h-5 mr-2 border-b pr-3 border-gray-300 focus:outline-none hover:outline-none"
            type={type}
            name="startTime"
            value={startTime}
            onChange={onChange}
          />
          <div>~</div>
          <input
            className="flex-1 h-5 ml-2 border-b border-gray-300 focus:outline-none hover:outline-none"
            type={type}
            name="finishTime"
            value={finishTime}
            onChange={onChange}
          />
        </div>
      </div>
    );
  };
  
 
  