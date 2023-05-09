"use client";
import Link from "next/link"

export default function Navi() {
  return (
    <div>
      <div className="p-4 border-r-2 border-solid border-stone-300 w-60 h-full flex flex-col">
      <Link href="/worker">
          <button className={`flex p-2 w-full ${
              window.location.pathname === "/worker"
                ? "bg-lime-700 text-white"
                : "bg-lime-100"
            } mb-1 rounded-sm hover:bg-lime-500 hover:text-white`}
          >
            근로자 메뉴
          </button>
        </Link>
      <Link href="/worker">
        <button className={`flex p-2 w-full ${
              window.location.pathname === "/worker"
                ? "bg-lime-700 text-white"
                : "bg-lime-100"
            } mb-1 rounded-sm hover:bg-lime-500 hover:text-white`}
          >
            나의 계약
          </button>
        </Link>
      <Link href="/worker/mywork">
        <button className={`flex p-2 w-full ${
              window.location.pathname === "/worker/mywork"
                ? "bg-lime-700 text-white"
                : "bg-lime-100"
            } mb-1 rounded-sm hover:bg-lime-500 hover:text-white`}
          >
            나의 근무
          </button>
        </Link>
      <Link href="/worker">
        <button className={`flex p-2 w-full ${
              window.location.pathname === "/worker/mywork"
                ? "bg-lime-700 text-white"
                : "bg-lime-100"
            } mb-1 rounded-sm hover:bg-lime-500 hover:text-white`}
          >
            급여 명세서
          </button>
        </Link>
      </div>
    </div>
  );
}