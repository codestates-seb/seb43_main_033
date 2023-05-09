import "./globals.css";
import { Inter } from "next/font/google";
import Header from "./Header";
import ManagerHome from "./manager/page";
import Footer from "./Footer";

const inter = Inter({ subsets: ["latin"] });

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html>
      <div>
        <Header />
        <body className={inter.className}>
          <header className="bg-white h-28 drop-shadow-lg w-full z-10 top-0 fixed"></header>
          <div className="flex">
            <div className="flex mt-28 w-full">{children}</div>
          </div>
          <div className="flex-grow"></div>
        </body>
        <Footer />
      </div>      
    </html>
  );
}
