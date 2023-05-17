import { data } from "autoprefixer";
import { ChangeEvent, useState } from "react";
import DatePicker from "react-datepicker";
import { format } from "date-fns";
import axios from "axios";

export default function WorkingStatusAdd() {
  const [startDate, setStartDate] = useState(new Date());
  const [finishDate, setFinishDate] = useState(new Date());
  const [statusDate, setStatusDate] = useState("");
  const [statusFinishDate, setStatusFinishDate] = useState("");
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
    console.log(status);
  };

  const handleTime = (date : any) => {
    setStartDate(date);
    const newDate = format(date, "yyyy-MM-dd'T'HH:mm:ss.SSS");
    setStatusDate(newDate);
  };
  const handleTime2 = (date  : any) => {
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
  );
}
