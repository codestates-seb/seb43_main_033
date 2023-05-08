import Link from "next/link"

export default function Navi() {
  return (
    <div>
      <div className="p-4 border-r-2 border-solid border-stone-300 w-60 h-full flex flex-col">
      <button className='p-5 '><Link href="/worker">Home</Link></button>
      <button className='p-5 '><Link href="/worker/">나의 계약</Link></button>
      <button className='p-5 '><Link href="/worker/mywork">나의 근무</Link></button>
      <button className='p-5 '><Link href="/worker">나의 급여명세서</Link></button>
      </div>
    </div>
  );
}