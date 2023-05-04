import Modal from "../components/HomeComponents/Modal";
import TopInformation from "../components/HomeComponents/TopInformation";
import MiddleInformation from "../components/HomeComponents/MiddleInformation";
import BottomInformation from "../components/HomeComponents/BottomInformation";

export default function ManagerHome() {
  return (
    <>
      {/* <Modal /> */}
      <section className="flex-1 flex flex-col px-8 py-4 bg-stone-50">
        <TopInformation />
        <MiddleInformation />
        <BottomInformation />
      </section>
    </>
  );
}
