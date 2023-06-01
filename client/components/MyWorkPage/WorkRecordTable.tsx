"use client";
import React, { useState, useEffect } from "react";
import { ChangeEvent } from "react";
import axios from "axios";
import moment from "moment";
import { Event as CalendarEvent } from "react-big-calendar";
import { useRouter } from "next/router";

interface WorkRecordTableProps {
  date: Date;
  
  closeDialog: () => void;
}

type WorkRecord = {
  id: number;
  companyId: number;
  companyName: string;
  memberId: number;
  memberName: string;
  startTime: string;
  finishTime: string;
  note: string;
};

type ApiResponse = {
  company: {
    companyId: number;
    companyName: string;
  }[];
  status: WorkRecord[];
};

const WorkRecordTable = ({
  date,

  closeDialog,
}: WorkRecordTableProps) => {
  const [companyId, setCompanyId] = useState<number>(1);
  const [startWork, setStartWork] = useState<string>("00:00");
  const [endWork, setEndWork] = useState<string>("00:00");
  const today: string = new Date().toISOString().slice(0, 10);
  const [workTime, setWorkTime] = useState<string>("0h 0m");
  const [note, setNote] = useState<string>("");
  const [noteData, setNoteData] = useState<string>("");
  const [isEditing, setIsEditing] = useState<boolean>(false);
  const [isStartButtonDisabled, setIsStartButtonDisabled] =
    useState<boolean>(false);
  const [isEndButtonDisabled, setIsEndButtonDisabled] =
    useState<boolean>(false);
  const [statusOfWorkId, setStatusOfWorkId] = useState<number | null>(null);

  const router = useRouter();

  useEffect(() => {
    fetchWorkRecord();
  }, [date]);

  const fetchWorkRecord = () => {
    const year = moment(date).year();
    const month = moment(date).month() + 1;

    axios
      .get(
        `${process.env.NEXT_PUBLIC_URL}/worker/mywork?year=${year}&month=${month}`,
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: localStorage.getItem("token"),
          },
        }
      )
      .then((response) => {
        const data: ApiResponse = response.data;
        const companyId = data.company[0]?.companyId;
        setCompanyId(companyId);
        const workRecord: WorkRecord | undefined = data.status.find(
          (record: WorkRecord) => moment(record.startTime).isSame(date, "day")
        );

        if (workRecord) {
          setNoteData(workRecord.note);
        }

        if (workRecord) {
          setStartWork(moment(workRecord.startTime).format("HH:mm"));
          setEndWork(moment(workRecord.finishTime).format("HH:mm"));
          setNote(workRecord.note);
          setIsStartButtonDisabled(!workRecord.startTime);
          setIsEndButtonDisabled(!workRecord.finishTime);
          setIsEditing(true);
          if (workRecord.id) {
            setStatusOfWorkId(workRecord.id);
          }
        } else {
          setStartWork("00:00");
          setEndWork("00:00");
          setNote("");
          setIsStartButtonDisabled(false);
          setIsEndButtonDisabled(false);
          setIsEditing(false);
        }
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const handleStartWork = () => {
    if (startWork !== "00:00") {
      alert("이미 출근 시간이 기록되었습니다.");
      return;
    }
    const currentTime = moment().format("HH:mm");
    setStartWork(currentTime);
    setIsStartButtonDisabled(true);
  };

  const handleEndWork = () => {
    if (startWork === "00:00") {
      alert("출근을 먼저 눌러야 퇴근을 누를 수 있습니다.");
      return;
    }

    const currentTime = moment().format("HH:mm");
    setEndWork(currentTime);

    const currentDate = moment().format("YYYY-MM-DD");
    const start = moment(`${currentDate} ${startWork}`);
    const end = moment(`${currentDate} ${currentTime}`);
    const duration = moment.duration(end.diff(start));
    const hours = Math.floor(duration.asHours());
    const minutes = duration.minutes();
    setWorkTime(`${hours}h ${minutes}m`);

    setIsEndButtonDisabled(true);
  };

  const handleNoteChange = (e: ChangeEvent<HTMLSelectElement>) => {
    setNote(e.target.value);
  };

  const handleSubmit = () => {

    axios
      .post(
        `${process.env.NEXT_PUBLIC_URL}/worker/mywork`,
        {
          companyId: companyId,
          startTime: `${today}T${startWork}:00.000`,
          finishTime: `${today}T${endWork}:00.000`,
          note: note,
        },
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: localStorage.getItem("token"),
          },
        }
      )
      .then(() => {
        setIsEditing(true);
        router.reload();
        fetchWorkRecord();
        closeDialog();
      })
      .catch(() => {
        alert('근로계약서를 먼저 작성해주세요')
      });
  };
  const handleEdit = (
    statusOfWorkId: number,
  ) => {
    axios
      .patch(
        `${process.env.NEXT_PUBLIC_URL}/status/${statusOfWorkId}`,
        {
          startTime: `${today}T${startWork}:00.000`,
          finishTime: `${today}T${endWork}:00.000`,
          note: note,
        },
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: localStorage.getItem("token"),
          },
        }
      )
      .then(() => {
       
        fetchWorkRecord();
        router.reload();
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const handleDelete = (statusOfWorkId: number, event?: CalendarEvent) => {
    axios
      .delete(`${process.env.NEXT_PUBLIC_URL}/status/${statusOfWorkId}`, {
        headers: {
          "Content-Type": "application/json",
          Authorization: localStorage.getItem("token"),
        },
      })
      .then(() => {
        setIsEditing(false);
        fetchWorkRecord();
      
        closeDialog();
        router.reload();
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return (
    <div>
      <h2 className="pb-2">
        {moment(date).format("YYYY년 MM월 DD일")} 근무 기록
      </h2>
      <table>
        <thead>
          <tr>
            <th>출근 시각</th>
            <th className="pl-3">퇴근 시각</th>
            <th className="pl-3">근무 시간</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td className="pl-3 pb-3">{startWork}</td>
            <td className="pl-6 pb-3">{endWork}</td>
            <td className="pl-6 pb-3">{workTime}</td>
           
          </tr>
          <tr>
          <div>{noteData}</div>
          </tr>
          <tr>
            <td>
              <strong>특이사항</strong>
            </td>
            <td colSpan={2}>
              <select
                className="border-b border-gray-300 focus:outline-none hover:outline-none"
                value={note}
                onChange={handleNoteChange}
              >
                <option value="">선택하세요</option>
                <option value="없음">없음</option>
                <option value="지각">지각</option>
                <option value="조퇴">조퇴</option>
                <option value="결근">결근</option>
                <option value="연장근로">연장근로</option>
                <option value="휴일근로">휴일근로</option>
                <option value="야간근로">야간근로</option>
                <option value="유급휴가">유급휴가</option>
                <option value="무급휴가">무급휴가</option>
              </select>
            </td>
          </tr>
        </tbody>
      </table>
      <button
        className={`bg-green-500 text-white font-bold py-2 px-3 rounded m-3 hover:bg-green-600 ${
          isStartButtonDisabled ? "opacity-50" : ""
        }`}
        onClick={handleStartWork}
        disabled={isStartButtonDisabled}
      >
        출근
      </button>
      <button
        className={`bg-green-500 text-white font-bold py-2 px-3 rounded m-3 hover:bg-green-600 ${
          isEndButtonDisabled ? "opacity-50" : ""
        }`}
        onClick={handleEndWork}
        disabled={isEndButtonDisabled}
      >
        퇴근
      </button>
      <div className="flex justify-end">
        <button
          onClick={
            isEditing && statusOfWorkId
              ? () => handleEdit(statusOfWorkId)
              : handleSubmit
          }
        >
          {isEditing ? "Update" : "Submit"}
        </button>
        <button
          className="pl-2"
          onClick={
            statusOfWorkId ? () => handleDelete(statusOfWorkId) : undefined
          }
        >
          Delete
        </button>
      </div>
    </div>
  );
};

export default WorkRecordTable;
