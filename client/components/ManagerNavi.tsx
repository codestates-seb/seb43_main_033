"use client";
import Link from "next/link";
import { useRouter } from "next/router";

export default function Navi() {
  const router = useRouter();
  const { pathname } = router;
  return (
    <div>
      <div className="p-4 border-r-2 border-solid border-stone-300 w-60 h-full flex flex-col">
        <Link href="/manager">
          <button
            className={`flex p-2 w-full ${
              pathname === "/manager"
                ? "bg-emerald-500 text-white"
                : "bg-emerald-100"
            } mb-1 rounded-sm hover:bg-emerald-300 hover:text-white`}
          >
            관리자 메뉴
          </button>
        </Link>
        <Link href="/manager/mystaff">
          <button
            className={`flex p-2 w-full ${
              pathname === "/manager/mystaff"
                ? "bg-emerald-500 text-white"
                : "bg-emerald-100"
            } mb-1 rounded-sm hover:bg-emerald-300 hover:text-white`}
          >
            나의 직원들
          </button>
        </Link>
        <Link href="/manager/paystub">
          <button
            className={`flex p-2 w-full ${
              pathname === "/manager/paystub"
                ? "bg-emerald-500 text-white"
                : "bg-emerald-100"
            } mb-1 rounded-sm hover:bg-emerald-300 hover:text-white`}
          >
            명세서
          </button>
        </Link>
        <Link href="/manager/authenticate">
          <button
            className={`flex p-2 w-full ${
              pathname === "/manager/authenticate"
                ? "bg-emerald-500 text-white"
                : "bg-emerald-100"
            } mb-1 rounded-sm hover:bg-emerald-300 hover:text-white`}
          >
            사업자 등록증
          </button>
        </Link>
      </div>
    </div>
  );
}
