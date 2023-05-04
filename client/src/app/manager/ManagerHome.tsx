import Modal from "./HomeComponents/Modal";
import TopInformation from "./HomeComponents/TopInformation";
import MiddleInformation from "./HomeComponents/middleInformation";
import BottomInformation from "./HomeComponents/BottomInformation";

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
