import Link from "next/link";
import Logo from "./logo";

export default function Header() {
  return (
    <header className="bg-white h-28 drop-shadow-lg z-10 w-screen top-0 fixed ">
      <div className="bg-emerald-400 h-8 flex justify-end align-middle">
        
          <Link href="/login"><button className="px-5 ">로그인</button></Link>
        
        
          <Link href="/signup"><button className="px-5">회원가입</button></Link>
        
      </div>
      <div className="flex justify-evenly align-middle h-20">
        <link
          rel="stylesheet"
          href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0"
        />
        <Logo />
        
          <Link href="/manager"><button className="text-2xl">관리자 메뉴</button></Link>
        
        
          <Link href="/worker"><button className="text-2xl">근로자 메뉴</button></Link>
        
        <span></span>
        <span></span>
        <span></span>
        <span></span>
      </div>
    </header>
  );
}
