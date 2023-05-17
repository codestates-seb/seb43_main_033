"use client";
import { useState } from "react";
import Bigsquare from "../../components/Bigsquare";
import ListBox from "../../components/ListBox";
import MyStaffModal from "../../components/MyStaffModal";
import Navi from "../../components/ManagerNavi";
import { format } from "date-fns";
import CheckBoxIcon from "@mui/icons-material/CheckBox";
import EditOutlinedIcon from "@mui/icons-material/EditOutlined";

interface MyStaffData {
  staffnumber: number;
  name: string;
  department: string;
  position: string;
  note: string;
  startTime: string;
  finishTime: string;
  grade: string;
  salary: number;
}

export default function Mystaff() {
  const today = format(new Date(), "yyyy.MM.dd");

  const data: MyStaffData[] = [
    {
      staffnumber: 2023001,
      name: "홍길동",
      department: "회계팀",
      position: "사원",
      note: "지각",
      startTime: "00:00",
      finishTime: "00:00",
      grade: "standard",
      salary: 4000000,
    },
    {
      staffnumber: 2023002,
      name: "김철수",
      department: "마케팅팀",
      position: "대리",
      note: "결근",
      startTime: "10:00",
      finishTime: "19:00",
      grade: "manager",
      salary: 4500000,
    },
    //...
  ];

  const [showModal, setShowModal] = useState(false);
  return (
    <>
      <Navi />
      <div className="flex flex-col w-full">
        <div className="flex justify-end pb-3">
          <h1 className="pr-10">우리 회사의 근무시간 : 09:00~18:00</h1>
          <i className="material-icons"></i>
          <CheckBoxIcon />
          <h1 className="mr-8">오늘의 근무상황</h1>
        </div>
        <div className="h-screen w-full flex justify-center">
          <Bigsquare>
            <div className="bg-white p-3 m-5 flex justify-between">
              <div>나의 직원들</div>
              <div>{today}</div>
            </div>

            <ListBox>
              <div>사번</div>
              <div className="lg:pl-10">이름</div>
              <div className="lg:pl-2">부서</div>
              <div className="lg:pl-1">직급</div>
              <div>근무상황</div>
              <div>기본근무시간</div>
              <div className="sm:mr-20 lg:mr-40 lg:pr-20">관리권한</div>
            </ListBox>
            {data && (
              <>
                {data.map((item, index) => (
                  <ListBox key={index}>
                    <div>{item.staffnumber}</div>
                    <div>{item.name}</div>
                    <div>{item.department}</div>
                    <div>{item.position}</div>
                    <div>
                      {item.finishTime} {item.note}
                    </div>
                    <div>
                      {item.startTime}~{item.finishTime}
                    </div>
                    <div className="pr-5">{item.grade}</div>
                    <button
                      className="text-sm font-bold hover:bg-gray-300"
                      onClick={() => setShowModal(true)}
                    >
                      <EditOutlinedIcon />
                    </button>

                    {showModal && (
                      <MyStaffModal
                        onClose={() => setShowModal(false)}
                      >
                        
                      </MyStaffModal>
                    )}
                  </ListBox>
                ))}
              </>
            )}
          </Bigsquare>
        </div>
      </div>
    </>
  );
}
