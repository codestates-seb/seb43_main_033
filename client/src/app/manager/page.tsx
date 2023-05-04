import Modal from "../HomeComponents/Modal";
import Information from "../HomeComponents/Information";
import Navi from "../Navi"


export default function ManagerHome() {
  return (
    <>
    <Navi />
      {/* <Modal /> */}
      <section className="flex-1 flex flex-col px-8 py-4 bg-stone-50">
        <Information />
        <article className="flex flex-row flex-wrap min-h-32 bg-white p-3 mb-5 rounded drop-shadow">
          <section className="flex-1 flex-col p-4">
            <div className="flex justify-between bg-stone-200 p-2 mb-3 rounded">
              <span className="">저번 달 급여내역</span>
            </div>
            <div className="flex flex-col p-2 bg-stone-100 min-h-32 rounded">
              <span>안녕</span>
            </div>
          </section>
          <section className="flex-1 flex-col p-4">
            <div className="flex justify-between bg-stone-200 p-2 mb-3 rounded">
              <span className="">이번 달 급여내역</span>
            </div>
            <div className="flex flex-col p-2 bg-stone-100 min-h-32 rounded">
              <span>안녕</span>
            </div>
          </section>
        </article>
        <article className="flex flex-col flex-wrap min-h-32 bg-white p-6 mb-5 rounded drop-shadow">
          <div className="flex justify-between items-center bg-stone-200 p-2 mb-3 rounded">
            <span className="mx-4 font-semibold text-2xl">
              이번 달 특이사항
            </span>
            <span className="mx-4 bg-stone-50 px-2 py-1 rounded-sm font-semibold drop-shadow">
              수정
            </span>
          </div>
          <section className="flex flex-colmin-h-32">안녕</section>
        </article>
      </section>
    </>
  );
}
