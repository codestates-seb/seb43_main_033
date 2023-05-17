import React, { PropsWithChildren } from "react";


export default function ListBox({ children }: PropsWithChildren) {
  return (
    <div className="bg-white p-2 m-5 flex justify-between">
      {children}
    </div>
  );
}