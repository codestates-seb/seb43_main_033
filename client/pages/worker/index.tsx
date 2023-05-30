import Link from "next/link";
import Navi from "../../components/WorkerNavi";
import TopInformation from "../../components/WorkerHome/TopInformation";
import MiddleInformation from "../../components/WorkerHome/MiddleInformation";
import BottomInformation from "../../components/WorkerHome/BottomInformation";

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
