import Image from 'next/image';
import { Inter } from "next/font/google";
import { Container } from "postcss";


const inter = Inter({ subsets: ["latin"] });

export default function Home() {
  return (    
    <main className="flex w-screen h-screen">
    <div className="p-4 border-r-2 border-solid border-stone-300 w-1/2 h-screen bg-teal-300">
      <div className="h-1/2 flex p-10">
        <div className=" flex flex-wrap justify-center w-1/2 content-center"><div className='box-border rounded bg-blue-400'>‘나의 직원들’의 급여명세서를 손 쉽게 작성</div>
        <Image src={'/writePaystub.jpg'} alt={''} width={491} height={324} className='w-auto p-2'/>       
        </div>
        <span className='absolute left-1/4 top-1/3 pl-5'>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0" />
        <span className="material-symbols-outlined">arrow_forward</span>
        </span>
        <div className="w-1/2  p-2 justify-center flex pl-20 flex-wrap bg-">
        <Image src={'/paystub.jpg'} alt={''} width={491} height={324} className='pb-2'/>
        <div className='box-border h-8 w-32 flex justify-center bg-lime-400 items-center'>
          명세서 작성<span className="material-symbols-outlined text-rose-800">left_click</span> 
          </div>                    
        </div>
      </div>
      <span className='box-border rounded bg-blue-400 absolute left-[43%] top-[28%] '>'생성과 동시에 직원에게 발송 ! '</span>
      <span className="material-symbols-outlined absolute left-1/2 top-1/3 pl-16">mark_email_unread</span>
      <span className="material-symbols-outlined absolute left-1/2 top-1/3 pl-5">double_arrow</span>
      <span className="material-symbols-outlined absolute right-1/2 top-1/3 ">double_arrow</span>
      <span className="material-symbols-outlined absolute right-1/2 top-1/3 pr-10">double_arrow</span>
      <span className="material-symbols-outlined absolute left-[49%] top-[85%]">arrows_outward</span>
      
      <div className="h-1/2 flex items-center">
        <div>
        <Image src={'/myWorker1.jpg'} alt={''} width={1026} height={477} className='w-screen p-2 justify-items-center'/>
        
        </div>
        <span className='w-1/4 box-border rounded bg-blue-400'>‘나의 직원들’ 을 통해 근무간의 특이사항 확인가능!
        <span className="material-symbols-outlined text-rose-800 absolute bottom-[15%] left-[40%]">left_click</span>
        </span>
      </div>
    </div>
    <div className="p-4 w-1/2 bg-lime-400 h-screen">
        <div className="h-1/2 flex flex-wrap justify-center p-10">
          <div className='box-border rounded bg-blue-400'>매달 ‘나의 급여명세서’ 확인 가능!
          </div>
          <div>
          <Image src={'/myPaystub.jpg'} alt={''} width={1000} height={700} className='w-auto p-2 h-auto pr-10'/>       
          </div>
        </div>
        <div className="h-1/2 flex items-center">        
          <span className='w-1/4 box-border rounded bg-blue-400'>‘나의 근무’ 를 통해 근무간의 특이사항 명시 가능 !
          </span>
          <div>
          <Image src={'/myWork2.jpg'} alt={''} width={1026} height={477} className='w-screen p-2'/>
          <span className="material-symbols-outlined text-rose-800 absolute left-[68%] top-[84%]">left_click</span>
          </div>
        </div>
    </div>        
  </main>
  )
}
