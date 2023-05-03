import { Inter } from "next/font/google";
import ManagerHome from "./ManagerHome";
import Link from "next/link"

const inter = Inter({ subsets: ["latin"] });

export default function Home() {
  return (
    <main className="flex pt-28">
     {/*<aside className="p-4 border-r-2 border-solid border-stone-300 w-60 h-screen">
        안녕
  </aside>
      <ManagerHome />*/}
    <Link href="/worker">worker</Link>
    <h1>home</h1>
    </main>
  );
}
