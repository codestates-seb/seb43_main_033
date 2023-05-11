import Link from "next/link";

export default function Logo() {
  return (
    <div className="flex">
      <button className="text-2xl">
        <Link href="/">
          <span className="material-symbols-outlined ">
            <span className="material-symbols-outlined absolute text-xs text-yellow-300">
              paid
            </span>
            feed
            <span className="text-blue-400">우리의급여</span>
          </span>
        </Link>
      </button>
    </div>
  );
}
