export default function ManagerHome() {
  const data = {
    image: "",
    corporateName: "법인명 예시",
    classified: "기업분류 예시",
    mainActivity: "주요산업 예시",
    representitive: "대표자명 예시",
    workersCount: "직원 수 에시",
  };
  return (
    <>
      <article className="p-2 items-center w-96 h-60 z-10 top-1/2 inset-x-1/2 absolute -translate-y-1/2 -translate-x-1/2 bg-white border-black border-solid border">
        asd
      </article>
      <section className="flex flex-col px-8 py-4 bg-stone-50 w-screen">
        <article className="flex flex-col flex-wrap min-h-32 bg-white p-6 mb-5 rounded drop-shadow">
          <div className="flex justify-between items-center bg-stone-200 p-2 mb-3 rounded">
            <span className="flex h-20 w-40 justify-center items-center p-1 border-black border-solid border rounded-sm">
              이미지
            </span>
            <button className="mr-4">수정</button>
          </div>
          <section className="flex flex-col">
            <div>
              <span className="flex">
                <label className="w-20">법인명: </label>
                <span>{data.corporateName}</span>
              </span>
            </div>
            <div>
              <span className="flex">
                <label className="w-20">기업 분류: </label>
                <span>{data.classified}</span>
              </span>
            </div>
            <div>
              <span className="flex">
                <label className="w-20">주요 산업: </label>
                <span>{data.mainActivity}</span>
              </span>
            </div>
            <div>
              <span className="flex">
                <label className="w-20">대표자명: </label>
                <span>{data.representitive}</span>
              </span>
            </div>
            <div>
              <span className="flex">
                <label className="w-20">주요 산업: </label>
                <span>{data.workersCount}</span>
              </span>
            </div>
          </section>
        </article>
        <article className="flex flex-col flex-wrap min-h-32 bg-white p-6 mb-5 rounded drop-shadow">
          <div className="flex justify-between bg-stone-200 p-2 mb-3 rounded">
            <span className="">안녕1</span>
            <span className="">수정</span>
          </div>
          안녕
        </article>
        <article className="flex flex-col flex-wrap min-h-32 bg-white p-6 mb-5 rounded drop-shadow">
          <div className="flex justify-between bg-stone-200 p-2 mb-3 rounded">
            <span className="">안녕1</span>
            <span className="">수정</span>
          </div>
          안녕
        </article>
      </section>
    </>
  );
}
