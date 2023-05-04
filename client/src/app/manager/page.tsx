import Link from "next/link";
import Navi from "../Navi";
import ManagerHome from "./ManagerHome";

export default function Manager() {
  return (
    <div className="flex w-screen">
      <Navi />
      <main className="flex-1 flex flex-col ">
        <ManagerHome />
      </main>
    </div>
  );
}
