import Image from 'next/image'
import { Inter } from 'next/font/google'
import { Container } from 'postcss'
import ManagerHome from './ManagerHome'

const inter = Inter({ subsets: ['latin'] })

export default function Home() {
  return (
    <div>
      <header className="bg-white h-28 drop-shadow-lg w-screen z-10 top-0 fixed justify-center">
        <div className='bg-emerald-400 h-8 flex justify-end align-middle'>
          <div className='px-5'>로그인</div>
          <div className='px-5'>회원가입</div>
        </div>
      </header>
      <main className="flex pt-28">
        <aside className="border border-solid border-black h-screen">
          안녕
        </aside>
        <ManagerHome />
      </main>
    </div>
  )
}
