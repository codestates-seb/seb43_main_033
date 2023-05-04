import Link from "next/link"
import Navi from "../Navi"

export default function Manager() {
  return (
    <div className="flex">
    <Navi />
    <main className="max-w-screen-sm mx-auto flex flex-col pt-28 m-16">
      <article className="bg-white p-6 border border-solid border-black h-screen/4 w-full">
        관리자 전용 페이지
        
      </article>
      <Link href="/mystaff">나의 직원들 </Link>
      <Link href="/paystub">급여 명세서 작성 </Link>
    </main>
    </div>
  )
}
