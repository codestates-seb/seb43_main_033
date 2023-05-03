import Image from 'next/image'
import { Inter } from 'next/font/google'
import { Container } from 'postcss'
import ManagerHome from "./ManagerHome";
import Header from "./Header";
// import Footer from "../layouts/Footer/Footer";
import Navi from "./Navi";
import Link from "next/link"

const inter = Inter({ subsets: ["latin"] });

export default function Home() {
  return (
    <main className="flex pt-28">
    <Link href="/worker">worker</Link>
    <h1>home</h1>
    </main>
  );
}
