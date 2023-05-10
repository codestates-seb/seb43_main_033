import { data } from "autoprefixer";
import {
  ChangeEvent,
  ChangeEventHandler,
  OptionHTMLAttributes,
  useState,
} from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { format } from "date-fns";
import axios from "axios";

export default function WorkingStatus() {
  const [startDate, setStartDate] = useState(new Date());
  const [finishDate, setFinishDate] = useState(new Date());
  const [statusDate, setStatusDate] = useState("");
  const [statusFinishDate, setStatusFinishDate] = useState("");
  const [status, setStatus] = useState("지각");
  const [add, setAdd] = useState(false);
  const statusArr = [
    "지각",
    "조퇴",
    "결근",
    "연장근로",
    "휴일근로",
    "야간근로",
    "유급휴가",
    "무급휴가",
  ];
  const handleStatus = (e: ChangeEvent<HTMLSelectElement>) => {
    setStatus(e.target.value);
    console.log(status);
  };

  const handleTime = (date) => {
    setStartDate(date);
    const newDate = format(date, "yyyy-MM-dd'T'HH:mm:ss.SSS");
    setStatusDate(newDate);
  };
  const handleTime2 = (date) => {
    setFinishDate(date);
    const newDate = format(date, "yyyy-MM-dd'T'HH:mm:ss.SSS");
    setStatusFinishDate(newDate);
  };
  const handleSubmit = () => {
    let workStatusdata = {
      companyId: 1,
      memberId: 1,
      startTime: statusDate,
      finishTime: statusFinishDate,
      note: status,
    };
    console.log(workStatusdata);
    axios
      .post(
        "https://c49c-61-254-8-200.ngrok-free.app/statusofworks",
        workStatusdata
      )
      .then((res) => console.log(res))
      .catch((err) => console.log(err));
  };
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
      {add ? (
        <div className=" flex border p-2">
          <div className="flex">
            <select onChange={(e) => handleStatus(e)}>
              {statusArr.map((el, idx) => (
                <option key={idx} value={el}>
                  {el}
                </option>
              ))}
            </select>
            <DatePicker
              selected={startDate}
              onChange={(date) => handleTime(date)}
              timeInputLabel="Time:"
              dateFormat="MM/dd/yyyy h:mm aa"
              showTimeInput
            />
            <DatePicker
              selected={finishDate}
              onChange={(date) => handleTime2(date)}
              timeInputLabel="Time:"
              dateFormat="MM/dd/yyyy h:mm aa"
              showTimeInput
            />
          </div>
          <button className="ml-10 " onClick={handleSubmit}>
            submit
          </button>
        </div>
      ) : null}
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

export const koreanTime = (date) => {
  const dateStr = new Date(date);
  const koreanTimeStr = dateStr.toLocaleString("ko-KR", {
    timeZone: "Asia/Seoul",
  });
  return koreanTimeStr;
};
