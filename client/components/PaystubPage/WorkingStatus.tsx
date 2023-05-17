import { useState } from "react";
import "react-datepicker/dist/react-datepicker.css";

import WorkingStatusAdd from "./WorkingStatusAdd";

export default function WorkingStatus() {
  const [add, setAdd] = useState(false);

  return (
    <div className="flex flex-col mb-20 ml-10">
      <div>
        {workingStatusDummydata.map((el, idx) => {
          return (
            <div key={el.id} className="flex mb-5">
              <div className="mr-[38px]">{el.note}</div>
              <div className="mr-[50px]"> {koreanTime(el.startTime)}</div>
              <div> {koreanTime(el.finishTime)}</div>
              <div className="ml-7">
                <button className="text-[12px] text-gray-400 mr-3">edit</button>
                <button className="text-[12px] text-gray-400">delete</button>
              </div>
            </div>
          );
        })}
      </div>
      {add ? <WorkingStatusAdd /> : null}
      <div className="flex justify-end">
        <button
          onClick={() => setAdd((prev) => !prev)}
          className="bg-green-300 py-1 px-2 rounded-md text-white text-[13px] hover:bg-green-500 mt-5"
        >
          {add ? "Cancel" : "Add"}
        </button>
      </div>
    </div>
  );
}

interface WorkingStatusData {
  id: number;
  memberId: number;
  memberName: string;
  companyId: number;
  companyName: string;
  startTime: string;
  finishTime: string;
  note: string;
}

export const workingStatusDummydata = [
  {
    id: 1,
    memberId: 1,
    memberName: "난쟁이",
    companyId: 1,
    companyName: "난쟁컴퍼니",
    startTime: "2023-05-08T13:06:50.4675865",
    finishTime: "2023-05-08T13:06:50.4675865",
    note: "지각",
  },
  {
    id: 2,
    memberId: 1,
    memberName: "난쟁이",
    companyId: 1,
    companyName: "난쟁컴퍼니",
    startTime: "2023-05-08T13:06:50.4675865",
    finishTime: "2023-05-08T13:06:50.4675865",
    note: "지각",
  },
];

export const koreanTime = (date : any) => {
  const dateStr = new Date(date);
  const koreanTimeStr = dateStr.toLocaleString("ko-KR", {
    timeZone: "Asia/Seoul",
  });
  return koreanTimeStr;
};
