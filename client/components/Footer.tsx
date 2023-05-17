import Logo from "./logo";
import Link from "next/link";

export default function Footer() {
  return (
    <div className=" bottom-0 border-t-2 w-full justify-center grid bg-emerald-700">
      <div className="flex h-80 box-border pt-12 pr-0 pb-0 pl-0 flex-auto w-full mx-20 gap-20">
        <div className="flex mb-24">
          <Logo />
        </div>
        <nav className=" flex">
          <div className="box-border pr-12 flex-auto ">
            <h5 className="mb-8">
              
                <Link href="/">우리의 급여</Link>
              
            </h5>
            <ul className="inline-block align-top text-sm pt-2">
              <li>
                <a href="#none">Questions</a>
              </li>
              <li>
                <a href="#none">Help</a>
              </li>
            </ul>
          </div>
          <div className="box-border pr-12 flex-auto">
            <h5 className="mb-8">
              
                <Link href="/manager">관리자 메뉴</Link>
              
            </h5>
            <ul className="inline-block align-top text-sm pt-2">
              <li>
                <a className="">나의 직원들</a>
              </li>
              <li>
                <a className="">명세서 작성</a>
              </li>
              <li>
                <a className="">사업자 등록증 관리</a>
              </li>
            </ul>
          </div>
          <div className="box-border pr-12 flex-auto">
            <h5 className="mb-8">
              
                <Link href="/worker">근로자 메뉴</Link>
             
            </h5>
            <ul className="inline-block align-top text-sm pt-2">
              <li>
                <a>나의 계약</a>
              </li>
              <li>
                <a>나의 근무</a>
              </li>
              <li>
                <a>나의 급여명세서</a>
              </li>
            </ul>
          </div>
        </nav>
        <div className="flex flex-col">
          <ul className="flex">
            <li>
              <a className="inline-block align-top text-sm pr-2">Blog</a>
            </li>
            <li>
              <a className="inline-block align-top text-sm pr-2">Facebook</a>
            </li>
            <li>
              <a className="inline-block align-top text-sm pr-2">Twitter</a>
            </li>
            <li>
              <a className="inline-block align-top text-sm pr-2">LinkedIn</a>
            </li>
            <li>
              <a className="inline-block align-top text-sm pr-2">Instagram</a>
            </li>
          </ul>
          <p className="text-sm pt-32">경기도 수원시 권선구 서둔동.</p>
          <p className="text-sm">
            Copyrightⓒ National Tax Service. All rights reserved.
          </p>
        </div>
      </div>
    </div>
  );
}
