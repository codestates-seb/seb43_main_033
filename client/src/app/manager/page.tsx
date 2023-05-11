import Link from "next/link"
import Navi from "../components/managerNavi"
import TopInformation from "../components/ManagerHome/TopInformation"
import MiddleInformation from "../components/ManagerHome/MiddleInformation"
import BottomInformation from "../components/ManagerHome/BottomInformation"


export default function ManagerHome() {
  return (
    <>
      <Navi />
      <section className="flex-1 flex flex-col px-8 py-4 bg-stone-50">
        <TopInformation />
        <MiddleInformation />
        <BottomInformation />
      </section>
    </>
  );
}
