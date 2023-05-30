"use client";

export default function MiddleInformation() {
  return (
    <article className="flex flex-row flex-wrap min-h-32 bg-white p-3 mb-5 rounded drop-shadow">
      <section className="flex-1 flex-col p-4">
        <div className="flex justify-between bg-stone-200 p-2 mb-3 rounded">
          <span className="">저번 달 급여내역</span>
        </div>
        <div className="flex flex-col p-2 bg-stone-100 min-h-32 rounded">
          <div className="flex">
            <label className="w-36 mb-1">직원 수: </label>
            <span>NN명</span>
          </div>
          <div className="flex">
            <label className="w-36 mb-1">총 지급예정 급여: </label>
            <span>NNNNNNNN원</span>
          </div>
          <div className="flex">
            <label className="w-36 mb-1">원천세(소득세): </label>
            <span>NNNNNNNN원</span>
          </div>
          <div className="flex">
            <label className="w-36 mb-1">원천세(지방소득세): </label>
            <span>NNNNNNNN원</span>
          </div>
          <div className="flex">
            <label className="w-36 mb-1">4대보험(사업장지급): </label>
            <span>NNNNNNNN원</span>
          </div>
        </div>
      </section>
      <section className="flex-1 flex-col p-4">
        <div className="flex justify-between bg-stone-200 p-2 mb-3 rounded">
          <span className="">이번 달 급여내역</span>
        </div>
        <div className="flex flex-col p-2 bg-stone-100 min-h-32 rounded">
          <div className="flex">
            <label className="w-36 mb-1">직원 수: </label>
            <span>NN명</span>
          </div>
          <div className="flex">
            <label className="w-36 mb-1">총 지급예정 급여: </label>
            <span>NNNNNNNN원</span>
          </div>
          <div className="flex">
            <label className="w-36 mb-1">원천세(소득세): </label>
            <span>NNNNNNNN원</span>
          </div>
          <div className="flex">
            <label className="w-36 mb-1">원천세(지방소득세): </label>
            <span>NNNNNNNN원</span>
          </div>
          <div className="flex">
            <label className="w-36 mb-1">4대보험(사업장지급): </label>
            <span>NNNNNNNN원</span>
          </div>
        </div>
      </section>
    </article>
  );
}
