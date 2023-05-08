import Link from "next/link"

export default function Navi() {
  return (
    <div>
      <div className="p-4 border-r-2 border-solid border-stone-300 w-60 h-full flex flex-col">
      <button className='p-5 '><Link href="/manager">Home</Link></button>
      <button className='p-5 '><Link href="/manager/mystaff">나의 직원들</Link></button>
      <button className='p-5 '><Link href="/manager/paystub">명세서 작성</Link></button>
      <button className='p-5 '><Link href="/manager">사업자등록증</Link></button>
      </div>
    </div>
  );
}
