import Link from "next/link"

export default function Navi() {
    return (
      <div>        
        <aside className="p-4 border-r-2 border-solid border-stone-300 w-60 h-screen">
          <a>안녕</a>      
          <Link href="/mystaff">나의 직원들 </Link>
          <Link href="/paystub">급여 명세서 작성 </Link>    
        </aside>        
        
      </div>
    )
  }