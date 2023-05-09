"use client"
import { useState , useEffect} from "react";
import Image from 'next/image'
import defaultcontract from '../defaultcontract.png'
import axios from "axios";

type ModalProps = {
  children: React.ReactNode;
  onClose: () => void;
};

interface MyStaffData {
  staffnumber:  number;
  name: string;
  department: string;
  position: string;
  note : string,
  startTime: string;
  finishTime: string;
  grade: string;
  salary: number;
}
export default function StaffSelectModal({onClose }: ModalProps) {
/*const [stafflist] = staffAxios(
  `${process.env.REACT_APP_API_URL}/list/${memberId}`
);
useEffect(() => {
  if (stafflist && stafflist.data && stafflist.data.title) {
    setStaffnumberValue(stafflist.data.staffnumber);
    setStaffnumberValue(stafflist.data.name);
     setDepartmentValue(stafflist.datadepartment);
    setPostionValue(stafflist.datapostion);
     setStartTimeValue(stafflist.datastartTime);
  setFinishTimeValue(stafflist.data.finishTime);
  setSalaryValue(stafflist.data.salaryValue);
  }
}, [stafflist]);*/

const [staffnumberValue, setStaffnumberValue] = useState<string>("");
const [memberNameValue, setMemberNameValue] = useState<string>("");
const [departmentValue, setDepartmentValue] = useState<string>("");
const [positionValue, setPositionValue] = useState<string>("");
const [startTimeValue, setStartTimeValue] = useState<string>("");
const [finishTimeValue, setFinishTimeValue] = useState<string>("");
const [gradeValue, setGradeValue] = useState<string>("");
const [salaryValue, setSalaryValue] = useState<number>(0);


/*const handleEditClick= () => {
 

  axios
    .patch(
      `${process.env.REACT_APP_API_URL}`,
      {
         staffnumber: staffnumberValue,
         memberName:memberNameValue,
         department: departmentValue,
         position: positionValue,
         startTime: startTimeValue,
         finishTime: finishTimeValue,
         gradeValue: setGradeValue,
         salary: salaryValue,
      },
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: localStorage.getItem("token"),
        },
      }
    )
    .then(() => {
    <Link href="/mystaff"></Link>
    })
    .catch((err) => {
      console.log(err);
    });
};*/

  const data: MyStaffData = {
    staffnumber: 2023001,
    name: "홍길동",
    department:"회계팀",
    position:"사원",
    note : "지각",
    startTime:"00:00",
    finishTime: "00:00",
    grade: "standard",
    salary: 4000000,
  };

  return (
    <div className="fixed z-10 inset-0 overflow-y-auto flex items-center justify-center">
        <div className="flex items-center justify-center min-h-screen px-4">
        <div className="fixed inset-0 transition-opacity" aria-hidden="true">
          <div className="absolute inset-0 bg-gray-500 opacity-75"></div>
        </div>
        <div className="bg-white rounded-md z-10 w-full max-w-lg p-10 modal-content">
        <div className = "flex justify-between">
        <div>
        <Image className="w-40 h-40 pr-6"src={defaultcontract} alt="근로계약서" />
        <button className="bg-gray-200 p-2 mt-10 rounded-md text-sm hover:bg-gray-300">근로계약서 업로드</button>
        </div>
        <div>

          <div>
        <div className = "mt-2 mb-2 font-bold">사번</div>
        <input className="w-20 h-5 mb-2 border-b border-gray-300 focus:outline-none hover:outline-none"
              type="text"
            
              value={staffnumberValue}
              onChange={(e) => setStaffnumberValue(e.target.value)}
            />
          </div>

          <div>
        <div className = "mb-2 font-bold">이름</div>
        <input className="w-20 mb-2 h-5 border-b border-gray-300 focus:outline-none hover:outline-none"
              type="text"
             
              value={memberNameValue}
              onChange={(e) => setMemberNameValue(e.target.value)}
            />
            </div>

        <div>
        <div className = "mb-1 font-bold">부서</div>
        <input className="w-20 h-5 border-b border-gray-300 focus:outline-none hover:outline-none"
              type="text"
             
              value={positionValue}
              onChange={(e) => setPositionValue(e.target.value)}
            />
            </div>
        </div>

        <div>
          <div>
        <div className = "mt-2 mb-2 ml-8 font-bold">근무시간</div>
        <input className="w-20 h-5 ml-7 border-b pr-3 border-gray-300 focus:outline-none hover:outline-none"
         type="time"
         value={startTimeValue}
         onChange={(e) => setStartTimeValue(e.target.value)}/>
        <input className="w-20 h-5 ml-1 border-b border-gray-300 focus:outline-none hover:outline-none"
        type="time"
        value={finishTimeValue}
        onChange={(e) => setFinishTimeValue(e.target.value)}/>
        </div>

        <div>
        <div className = "mt-2 mb-1 ml-8 font-bold">권리권한</div>
        <input className="w-20 h-5 ml-8 mb-3 border-b border-gray-300 focus:outline-none hover:outline-none"
              type="text"
        
              value={gradeValue}
              onChange={(e) => setGradeValue(e.target.value)}
            />

        </div>

        <div>
        <div className="ml-7 font-bold">기본급:</div>
        <input className="w-20 h-5 ml-7 pb-2 border-b border-gray-300 focus:outline-none hover:outline-none"
              type="text"
              value={salaryValue}
              onChange={(e) => setSalaryValue(e.target.value)}
            />
        </div>
        </div>
        </div>
          <div className="modal-close flex justify-end">
          <button onClick={() => handleEditClick()}>Yes</button>
          <button className="ml-5" onClick={onClose}>No</button>
          </div>
        </div>
      </div>
    </div>
  );
}
