import Link from "next/link";
import Image from 'next/image';

export default function Logo() {
  return (
    <div className="flex">
      <button className="text-2xl">
        <Link href="/">
          <span className="material-symbols-outlined ">            
            <Image src={'/logo3.jpg'} alt={''} width={200} height={50} className='pb-2'/>
          </span>
        </Link>
      </button>
    </div>
  );
}
