"use client";
import { useEffect, useState } from "react";
import "react-datepicker/dist/react-datepicker.css";

import WorkingStatusAdd from "./WorkingStatusAdd";
import axios from "axios";

export default function WorkingStatus({
  selectedCompanyMemberId,
  companyId,
}: {
  selectedCompanyMemberId: any;
  companyId: any;
}) {
  const [add, setAdd] = useState(false);
  const [editId, setEditId] = useState<number | null>(null);
  const [workdata, setWorkData] = useState<WorkingStatusData[]>([]);
  const handleDelete = (deletedId: number) => {
    const token = localStorage.getItem("token");
    axios
      .delete(`${process.env.NEXT_PUBLIC_URL}/status/${deletedId}`, {
        headers: {
          Authorization: token,
        },
      })
      .then((res) => {})
      .catch((err) => console.log(err));
  };

  useEffect(() => {
    const token = localStorage.getItem("token");
    const currentDate = new Date();
    const month = currentDate.getMonth() + 1;
    const year = currentDate.getFullYear();
    {
      selectedCompanyMemberId && companyId
        ? axios
            .get(
              `${process.env.NEXT_PUBLIC_URL}/manager/${companyId}/members/${selectedCompanyMemberId}/paystub?year=${year}&month=${month}`,
              {
                headers: { Authorization: `${token}` },
              }
            )
            .then((res) => {
              setWorkData(res.data.status);
            })
            .catch((err) => {
              setWorkData([]);
              console.log(err);
            })
        : null;
    }
  }, [selectedCompanyMemberId, companyId]);

  return (
    <div className="flex flex-col mb-20 ml-10">
      <div>
        {workdata &&
          workdata.map((el, idx) => {
            return (
              <div key={idx} className="flex mb-5 flex-col">
                <div className="flex">
                  <div className="mr-[38px]">{el.note}</div>
                  <div className="mr-[50px]"> {koreanTime(el.startTime)}</div>
                  <div> {koreanTime(el.finishTime)}</div>
                  <div className="ml-7">
                    <button
                      className="text-[12px] text-gray-400 mr-3"
                      onClick={() => setEditId(el.statusId)}
                    >
                      edit
                    </button>
                    <button
                      className="text-[12px] text-gray-400"
                      onClick={() => handleDelete(el.statusId)}
                    >
                      delete
                    </button>
                  </div>
                </div>
                <div>
                  {editId === el.statusId ? (
                    <WorkingStatusAdd
                      editId={editId}
                      setEditId={setEditId}
                      add={add}
                      setAdd={setAdd}
                      startTime={el.startTime}
                      finishTime={el.finishTime}
                      selectedCompanyMemberId={selectedCompanyMemberId}
                      companyId={companyId}
                    />
                  ) : null}
                </div>
              </div>
            );
          })}
      </div>
      {add ? (
        <WorkingStatusAdd
          add={add}
          setAdd={setAdd}
          selectedCompanyMemberId={selectedCompanyMemberId}
          companyId={companyId}
        />
      ) : null}
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
  statusId: number;
  memberId: number;
  memberName: string;
  companyId: number;
  companyName: string;
  startTime: string;
  finishTime: string;
  note: string;
}

export const koreanTime = (date: Date | string) => {
  const dateStr = new Date(date);
  const koreanTimeStr = dateStr.toLocaleString("ko-KR", {
    timeZone: "Asia/Seoul",
  });
  return koreanTimeStr;
};
