import React, { PropsWithChildren } from "react";

interface TabButtonProps extends PropsWithChildren{
  selected: boolean
  onClick: () => void;
}

export default function TabButton({ selected, onClick, children }:TabButtonProps) {
  return (
    <button
      className={`tab-button ${selected ? "bg-emerald-300" : "bg-white"} p-5 px-10 rounded-md focus:outline-none hover:bg-emerald-200 transition-colors duration-300`}
      onClick={onClick}
    >
      {children}
    </button>
  );
};


