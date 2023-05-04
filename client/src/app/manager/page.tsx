import Link from "next/link";
import Navi from "../Navi";
import ManagerHome from "./ManagerHome";

export default function Manager() {
  return (
    <div className="flex w-screen">
      <Navi />
      {/* <Link href="/mystaff">나의 직원들 </Link>
      <Link href="/paystub">급여 명세서 작성 </Link> */}
      <main className="flex-1 flex flex-col ">
        <ManagerHome />
      </main>
    </div>
  );
}
