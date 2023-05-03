import Link from "next/link"
import Logo from "./components/logo"

export default function Header() {

    return (
        <header className="bg-white h-28 drop-shadow-lg w-screen top-0 fixed justify-center">
            <div className='bg-emerald-400 h-8 flex justify-end align-middle'>
                <button className='px-5 '>로그인</button>
                <button className='px-5'>회원가입</button>
            </div>
            <div className="flex justify-evenly align-middle h-20">
                <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0" />
                <Logo />
                <button className="text-2xl"><Link href="/manager">관리자 메뉴</Link></button>
                <button className="text-2xl"><Link href="/worker">근로자 메뉴</Link></button>
                <span></span>
                <span></span>
                <span></span>
                <span></span>
            </div>

      </header>
    )
}