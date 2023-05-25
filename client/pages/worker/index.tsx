import Navi from "../../components/WorkerNavi";
import TopInformation from "../../components/WorkerHome/TopInformation";

export default function ManagerHome() {
  return (
    <>
      <Navi />
      <section className="flex-1 flex flex-col px-8 py-4 bg-stone-50">
        <TopInformation />        
      </section>
    </>
  );
}
