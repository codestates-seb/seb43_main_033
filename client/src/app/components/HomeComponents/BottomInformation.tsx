export default function BottomInformation() {
  return (
    <article className="flex flex-col flex-wrap min-h-32 bg-white p-6 mb-5 rounded drop-shadow">
      <div className="flex justify-between items-center bg-stone-200 p-2 mb-3 rounded">
        <span className="mx-4 font-semibold text-2xl">이번 달 특이사항</span>
        <span className="mx-4 bg-stone-50 px-2 py-1 rounded-sm font-semibold drop-shadow">
          수정
        </span>
      </div>
      <section className="flex flex-col min-h-32">
        <span>안녕</span>
      </section>
    </article>
  );
}
