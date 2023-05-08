export default function MiddleInformation() {
  return (
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
          <span>안녕</span>
        </div>
      </section>
    </article>
  );
}
