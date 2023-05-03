import Image from 'next/image'
import { Inter } from 'next/font/google'
import { Container } from 'postcss'
import ManagerHome from "./ManagerHome";
import Header from "../layouts/Header";
// import Footer from "../layouts/Footer/Footer";
import Navi from "../layouts/Navi";

const inter = Inter({ subsets: ["latin"] });

export default function Home() {
  return (
    <div>
      <Header />
      <main className="flex pt-28">
      {/* <aside className="p-4 border-r-2 border-solid border-stone-300 w-60 h-screen">
        안녕
      </aside> */}
      <Navi />
      <ManagerHome />
      
      </main>
      {/* <Footer />     */}
    </div>
  )
}
