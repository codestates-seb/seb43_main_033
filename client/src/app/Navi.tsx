"use client";

import Link from "next/link";

export default function Navi() {
  console.log(window.location.pathname);
  return (
    <div>
      <aside className="flex flex-col p-4 border-r-2 border-solid border-stone-300 w-60 h-full">
        <Link href="/manager">
          <button
            className={`flex p-2 w-full ${
              window.location.pathname === "/manager"
                ? "bg-stone-500 text-white"
                : "bg-stone-100"
            } mb-1 rounded-sm hover:bg-stone-500 hover:text-white`}
          >
            관리자 메뉴
          </button>
        </Link>
        <Link href="/manager/example1">
          <button className="flex p-2 w-full bg-stone-100 mb-1 rounded-sm hover:bg-stone-500 hover:text-white">
            직원 관리
          </button>
        </Link>
        <Link href="/manager/example2">
          <button className="flex p-2 w-full bg-stone-100 mb-1 rounded-sm  hover:bg-stone-500 hover:text-white">
            어쩌구
          </button>
        </Link>
        <Link href="/manager/example3">
          <button className="flex p-2 w-full bg-stone-100 mb-1 rounded-sm  hover:bg-stone-500 hover:text-white">
            저쩌구
          </button>
        </Link>
      </aside>
    </div>
  );
}
