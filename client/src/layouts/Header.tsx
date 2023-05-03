export default function Header() {

    return (
        <header className="bg-white h-28 drop-shadow-lg w-screen z-10 top-0 fixed justify-center">
            <div className='bg-emerald-400 h-8 flex justify-end align-middle'>
                <button className='px-5 '>로그인</button>
                <button className='px-5'>회원가입</button>
            </div>
            <div className="flex justify-evenly align-middle h-20">
                <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0" />
                <div className="flex">                    
                    <button className="text-2xl">                         
                         <span className="material-symbols-outlined ">
                         <span className="material-symbols-outlined absolute text-xs text-yellow-300">paid</span> 
                         feed
                         <span className="text-blue-400">우리의급여</span>
                    </span>
                    </button>                                   
                </div>
                <button className="text-2xl">관리자 메뉴</button>
                <button className="text-2xl">근로자 메뉴</button>
                <span></span>
                <span></span>
                <span></span>
                <span></span>
            </div>

      </header>
    )
}