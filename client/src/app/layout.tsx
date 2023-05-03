import './globals.css'
import { Inter } from 'next/font/google'
import Header from './Header'
import Footer from './Footer'

const inter = Inter({ subsets: ["latin"] });

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html > 
    
              
      <div>
        <Header /> 
      <body className={inter.className}>
        <header className="bg-white h-28 drop-shadow-lg w-screen z-1 top-0 fixed"></header>
        
        <div className="flex">
          
          <div className="flex">{children}</div>
        </div>
      </body>
      </div>
      <Footer />
      
    </html>
  );
}
