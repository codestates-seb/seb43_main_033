import Link from "next/link";
import Navi from "../../components/ManagerNavi";
import TopInformation from "../../components/ManagerHome/TopInformation";
import MiddleInformation from "../../components/ManagerHome/MiddleInformation";
import BottomInformation from "../../components/ManagerHome/BottomInformation";

export default function ManagerHome() {
  return (
    <>
      <Navi />
      <section className="flex flex-col px-8 py-4 w-screen bg-stone-50">
        <TopInformation />
        <MiddleInformation />
        <BottomInformation />
      </section>
    </>
  );
}
