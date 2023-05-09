"use client";
import Link from "next/link"

export default function Navi() {
  console.log(window.location.pathname);
  return (
    <div>
      <div className="p-4 border-r-2 border-solid border-stone-300 w-60 h-full flex flex-col">
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
      <button className='p-5 '><Link href="/manager/mystaff">나의 직원들</Link></button>
      <button className='p-5 '><Link href="/manager/paystub">명세서 작성</Link></button>
      <button className='p-5 '><Link href="/manager">사업자등록증</Link></button>
      </div>
    </div>
  );
}
