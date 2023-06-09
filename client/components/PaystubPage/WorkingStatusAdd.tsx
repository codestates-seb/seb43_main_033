import {
  ChangeEvent,
  Dispatch,
  SetStateAction,
  useEffect,
  useState,
} from "react";
import DatePicker from "react-datepicker";
import { format } from "date-fns";
import axios from "axios";

export default function WorkingStatusAdd({
  editId,
  add,
  setAdd,
  setEditId,
  startTime,
  finishTime,
  selectedCompanyMemberId,
  companyId,
}: {
  editId?: number | null;
  setEditId?: Dispatch<SetStateAction<number | null>>;
  add?: boolean;
  setAdd?: Dispatch<SetStateAction<boolean>>;
  startTime?: string;
  finishTime?: string;
  selectedCompanyMemberId?: number;
  companyId?: number;
}) {
  const [startDate, setStartDate] = useState(new Date());
  const [finishDate, setFinishDate] = useState(new Date());
  const [statusDate, setStatusDate] = useState("");
  const [statusFinishDate, setStatusFinishDate] = useState("");
  useEffect(() => {
    startTime ? setStartDate(new Date(startTime)) : null;
  }, [startTime]);
  useEffect(() => {
    finishTime ? setFinishDate(new Date(finishTime)) : null;
  }, [finishTime]);
  const [status, setStatus] = useState("지각");
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
  };

  const handleTime = (date: any) => {
    setStartDate(date);
    const newDate = format(date, "yyyy-MM-dd'T'HH:mm:ss.SSS");
    setStatusDate(newDate);
  };
  const handleTime2 = (date: any) => {
    setFinishDate(date);
    const newDate = format(date, "yyyy-MM-dd'T'HH:mm:ss.SSS");
    setStatusFinishDate(newDate);
  };
  const handleSubmit = () => {
    let workStatusdata = {
      startTime: statusDate,
      finishTime: statusFinishDate,
      note: status,
    };
    const token = localStorage.getItem("token");
    axios
      .post(
        `${process.env.NEXT_PUBLIC_URL}/manager/${companyId}/members/${selectedCompanyMemberId}/status`,
        workStatusdata,
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: token,
          },
        }
      )
      .then((res) => {
        setAdd && setAdd(false);
      })
      .catch((err) => console.log(err));
  };
  const handleEditSubmit = () => {
    const token = localStorage.getItem("token");
    let workStatusdata = {
      startTime: statusDate,
      finishTime: statusFinishDate,
      note: status,
    };
    axios
      .patch(
        `${process.env.NEXT_PUBLIC_URL}/status/${editId}`,
        workStatusdata,
        {
          headers: {
            Authorization: token,
          },
        }
      )
      .then((res) => {
        setAdd && setAdd(false);
      })
      .catch((err) => console.log(err));
  };
  return (
    <div className="fixed pt-40 z-10 inset-0 overflow-y-auto">
      <div className="flex items-center justify-center min-h-screen px-4">
        <div className="fixed inset-0 transition-opacity" aria-hidden="true">
          <div className="absolute inset-0 bg-gray-500 opacity-75"></div>
        </div>
        <div className="bg-white rounded-md z-10 w-full max-w-[600px] p-10 modal-content">
          <div className="flex justify-end">
            <button
              className="ml-5 fond-bold mb-3"
              onClick={() => {
                setAdd && setAdd(false);
                setEditId ? setEditId(null) : null;
              }}
            >
              X
            </button>
          </div>
          <div className=" flex border p-2 rounded-sm bg-white">
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
                onChange={(date: any) => handleTime(date)}
                timeInputLabel="Time:"
                dateFormat="yyyy/MM/dd h:mm aa"
                showTimeInput
              />
              <DatePicker
                selected={finishDate}
                onChange={(date: any) => handleTime2(date)}
                timeInputLabel="Time:"
                dateFormat="yyyy/MM/dd h:mm aa"
                showTimeInput
              />
            </div>
          </div>
          <div className="flex justify-end">
            {editId ? (
              <button
                className="bg-green-300 text-white px-2 py-1 rounded-md mt-2 hover:bg-green-500"
                onClick={handleEditSubmit}
              >
                submit
              </button>
            ) : (
              <button
                className="bg-green-300 text-white px-2 py-1 rounded-md mt-2 hover:bg-green-500"
                onClick={handleSubmit}
              >
                submit
              </button>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}
