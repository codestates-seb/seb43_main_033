import { Inter } from "next/font/google";
import Link from "next/link"

const inter = Inter({ subsets: ["latin"] });

export default function Home() {
  return (
    <main className="flex pt-28">
    <Link href="/mystaff">mystaff</Link>
    <h1>home</h1>
    </main>
  );
}
