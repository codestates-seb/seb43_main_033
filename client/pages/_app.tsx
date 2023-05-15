import "../globals.css";
import { Inter } from "next/font/google";
import Header from "../components/Header";
import Footer from "../components/Footer";

const inter = Inter({ subsets: ["latin"] });

interface AppProps {
  Component: React.ComponentType;
  pageProps: any;
}

export default function App({ Component, pageProps }: AppProps) {
  return (
    <html>
      <div>
        <Header />
        <body className={inter.className}>
          {/* <header className="bg-white h-28 drop-shadow-lg w-full z-10 top-0 fixed"></header> */}
          <div className="flex">
            <div className="flex mt-28 w-full">
              <Component {...pageProps} />
            </div>
          </div>
          <div className="flex-grow"></div>
        </body>
        <Footer />
      </div>
    </html>
  );
}
