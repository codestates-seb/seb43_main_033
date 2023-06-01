import React from "react";
import MyCalendar from "../../../components/MyWorkPage/MyCalendar";
import Navi from "../../../components/WorkerNavi";

export default function Mywork() {
  return (
    <div className="flex">
      <Navi />
      <div className="mt-10 mb-5">
        <div className="pl-40">
          <MyCalendar />
        </div>
      </div>
    </div>
  );
}
