import React from "react";
import MyCalendar from "../../components/MyCalendar";
import Navi from "../../components/WorkerNavi";

export default function Mywork() {
  return (
    <div className="flex">
      <Navi />
      <div>
        <h1 className="pl-40 pt-20">9:00~18:00 (휴게시간 12:00~13:00)</h1>
        <div className="pl-40">
          <MyCalendar />
        </div>
      </div>
    </div>
  );
}
