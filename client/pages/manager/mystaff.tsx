"use client";
import { useState } from "react";
import Bigsquare from "../../components/Bigsquare";
import ListBox from "../../components/ListBox";
import MyStaffModal from "../../components/MyStaffPage/MyStaffModal";
import Navi from "../../components/ManagerNavi";
import { format } from "date-fns";
import CheckBoxIcon from "@mui/icons-material/CheckBox";
import EditOutlinedIcon from "@mui/icons-material/EditOutlined";

interface Props {
  id:number
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

  const [selectedItemId, setSelectedItemId] = useState<number | null>(null);
  const [showModal, setShowModal] = useState<boolean>(false);
  const [data, setdata] = useState<Props[]>([
    {
      id: 1,
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
      id: 3,
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
  ]);
  


  const openModalClick = (id: number) => {
    setSelectedItemId(id);
    setShowModal(true);
  };

  const closeModal = () => {
    setShowModal(false);
  };

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
              <div className="lg:pl-5">사번</div>
              <div className="lg:pl-10">이름</div>
              <div className="lg:pl-10">부서</div>
              <div className="lg:pl-7">직급</div>
              <div className="lg:pl-5">근무상황</div>
              <div className="lg:pl-7">기본근무시간</div>
              <div className="sm:mr-20 lg:mr-40 lg:pr-10">관리권한</div>
            </ListBox>
            {data && (
              <>
                {data.map((item) => (
                  <ListBox key={item.id}>
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
                      onClick={() => openModalClick(item.id)}
                    >
                      <EditOutlinedIcon />
                    </button>

                    {selectedItemId === item.id && showModal &&(
                      <MyStaffModal
                      onClose={closeModal}
                      selectedItemId={selectedItemId}
                      ></MyStaffModal>
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
