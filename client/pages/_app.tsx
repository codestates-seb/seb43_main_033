import "../globals.css";
import { Inter } from "next/font/google";
import Header from "../components/Header";
import Footer from "../components/Footer";

interface AppProps {
  Component: React.ComponentType;
  pageProps: any;
}

export default function App({ Component, pageProps }: AppProps) {
  return (
    <div>
      <Header />
      <div className="flex">
        <div className="flex mt-28 w-full">
          <Component {...pageProps} />
        </div>
      </div>
      <div className="flex-grow"></div>
      <Footer />
    </div>
  );
}
