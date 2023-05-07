import Image from 'next/image';
import { Inter } from "next/font/google";
import { Container } from "postcss";


const inter = Inter({ subsets: ["latin"] });

export default function Home() {
  return (
    <main className="flex w-full h-screen">
    <div className="p-4 border-r-2 border-solid border-stone-300 w-1/2 h-full bg-teal-300">
      <div className="h-1/2 flex p-10">
        <div className="flex flex-wrap justify-center w-1/2">‘나의 직원들’의 급여명세서를 손 쉽게 작성
        <Image src={'/writePaystub.jpg'} alt={''} width={491} height={324} className='w-auto p-2'/>       
        </div>
        <div className='absolute'>
          
        </div>
        <div className="w-1/2  p-2 justify-center flex pl-20 flex-wrap">
        <Image src={'/paystub.jpg'} alt={''} width={491} height={324} />
        명세서 작성   
        </div>
      </div>
      <div className="h-1/2 flex items-center">
        <div>
        <Image src={'/myWorker.jpg'} alt={''} width={1026} height={477} className='w-full p-2 justify-items-center'/>
        </div>
        <span className='w-1/4'>‘나의 직원들’ 을 통해 근무간의 특이사항 확인 가능 !
        </span>
      </div>
    </div>
    <div className="p-4 w-1/2 bg-lime-400 h-full">
        <div className="h-1/2 flex flex-wrap justify-center p-10">
          <div>매달 ‘나의 급여명세서’ 확인 가능 !
          </div>
          <div>
          <Image src={'/myPaystub.jpg'} alt={''} width={1000} height={700} className='w-auto p-2 h-auto'/>       
          </div>
        </div>
        <div className="h-1/2 flex items-center">        
          <span className='w-1/4'>‘나의 직원들’ 을 통해 근무간의 특이사항 확인 가능 !
          </span>
          <div>
          <Image src={'/myWork1.jpg'} alt={''} width={1026} height={477} className='w-full p-2'/>
          </div>
        </div>
    </div>        
  </main>
  )
}
