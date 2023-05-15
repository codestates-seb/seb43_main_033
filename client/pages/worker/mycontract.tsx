"use client";
import Bigsquare from "../../components/Bigsquare";
import Navi from "../../components/WorkerNavi";

export default function Mycontract() {
  return (
    <>
      <Navi />
      <div className="h-screen w-screen flex justify-center">
        <Bigsquare>
          <div className="bg-white p-3 m-5">
            <div>나의 계약</div>
          </div>
          <div
            className="flex items-center"
            style={{ minHeight: "calc(100vh - 6rem)" }}
          ></div>
        </Bigsquare>
      </div>
    </>
  );
}
