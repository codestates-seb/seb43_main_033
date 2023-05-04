export default function Information() {
  const data = {
    image: "",
    corporateName: "법인명 예시",
    classified: "기업분류 예시",
    mainActivity: "주요산업 예시",
    representitive: "대표자명 예시",
    workersCount: "직원 수 예시",
  };
  return (
    <article className="flex flex-col flex-wrap min-h-32 bg-white p-6 mb-5 rounded drop-shadow">
      <div className="flex justify-between items-center bg-stone-200 p-2 mb-3 rounded">
        <span className="flex h-20 w-40 justify-center items-center p-1 border-black border-solid border rounded-sm">
          이미지
        </span>
        <button className="mx-4 bg-stone-50 px-6 py-3 rounded font-semibold text-2xl drop-shadow">
          수정
        </button>
      </div>
      <section className="flex flex-col p-4 bg-stone-100 rounded">
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
  );
}
