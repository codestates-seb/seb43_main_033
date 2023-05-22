"use client";
import { useEffect, useState } from "react";
import "react-datepicker/dist/react-datepicker.css";

import WorkingStatusAdd from "./WorkingStatusAdd";
import axios from "axios";

export default function WorkingStatus() {
  const [add, setAdd] = useState(false);
  const [editId, setEditId] = useState<number | null>(null);
  // const [deletedId,setDeletedId] = useState<number|null>(null);
  const [workdata, setWorkData] = useState<WorkingStatusData[]>([]);
  const handleDelete = (deletedId: number) => {
    axios
      .delete(`${process.env.NEXT_PUBLIC_URL}/statusofworks/${deletedId}`)
      .then((res) => {
        console.log(res);
      })
      .catch((err) => console.log(err));
  };

  useEffect(() => {
    const token = localStorage.getItem("token");
    console.log(token);
    const currentDate = new Date();
    const month = currentDate
      .toLocaleString("default", { month: "long" })
      .slice(0, 1);
    const year = currentDate.getFullYear();
    axios
      .get(`${process.env.NEXT_PUBLIC_URL}/worker/mywork?year=2023&month=1`, {
        headers: { Authorization: `${token}` },
      })
      .then((res) => {
        setWorkData(res.data);
      })
      .catch((err) => console.log(err));
  }, []);

  return (
    <div className="flex flex-col mb-20 ml-10">
      <div>
        {workdata.map((el, idx) => {
          return (
            <div key={el.id} className="flex mb-5 flex-col">
              <div className="flex">
                <div className="mr-[38px]">{el.note}</div>
                <div className="mr-[50px]"> {koreanTime(el.startTime)}</div>
                <div> {koreanTime(el.finishTime)}</div>
                <div className="ml-7">
                  <button
                    className="text-[12px] text-gray-400 mr-3"
                    onClick={() => setEditId(el.id)}
                  >
                    edit
                  </button>
                  <button
                    className="text-[12px] text-gray-400"
                    onClick={() => handleDelete(el.id)}
                  >
                    delete
                  </button>
                </div>
              </div>
              <div>
                {editId === el.id ? (
                  <WorkingStatusAdd
                    editId={editId}
                    setEditId={setEditId}
                    add={add}
                    setAdd={setAdd}
                    startTime={el.startTime}
                    finishTime={el.finishTime}
                  />
                ) : null}
              </div>
            </div>
          );
        })}
      </div>
      {add ? <WorkingStatusAdd add={add} setAdd={setAdd} /> : null}
      <div className="flex justify-end">
        <button
          onClick={() => setAdd((prev) => !prev)}
          className="bg-green-300 py-1 px-2 rounded-md text-white text-[13px] hover:bg-green-500 mt-5"
        >
          {add ? "" : "Add"}
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

export const koreanTime = (date: Date | string) => {
  const dateStr = new Date(date);
  const koreanTimeStr = dateStr.toLocaleString("ko-KR", {
    timeZone: "Asia/Seoul",
  });
  return koreanTimeStr;
};
