"use client";
import { useState } from "react";
import Bigsquare from "../../components/Bigsquare";
import ListBox from "../../components/ListBox";
import MyStaffModal from "../../components/MyStaffPage/MyStaffModal";
import Navi from "../../components/ManagerNavi";
import { format } from "date-fns";
import CheckBoxIcon from "@mui/icons-material/CheckBox";
import EditOutlinedIcon from "@mui/icons-material/EditOutlined";
import StaffAxios from "../../components/MyStaffPage/StaffAxios";

type Staff = {
  companyMemberId: number;
  companyId: number;
  memberId: number;
  name: string;
  grade: string;
  team: string;
  status: string;
  roles: null;
};

export default function Mystaff() {
  const today = format(new Date(), "yyyy.MM.dd");

  const [selectedcompanyId, setSelectedcompanyId] = useState<number | null>(null);
  const [showModal, setShowModal] = useState<boolean>(false);
  
  const [staffList] = StaffAxios(
    `${process.env.NEXT_PUBLIC_URL}/companymembers?page=1&status=&companyId=4`
  );

  console.log(staffList);

  const openModalClick = (id: number) => {
    setSelectedcompanyId(id);
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
            {staffList &&staffList.data && (
              <>
                {staffList.data.map((item:Staff) => (
                  <ListBox key={item.companyMemberId}>
                    <div>{item.companyMemberId}</div>
                    <div>{item.name}</div>
                    <div>{item.team}</div>
                    <div>{item.grade}</div>
                    <div className="pr-5">{item.roles}</div>
                    <button
                      className="text-sm font-bold hover:bg-gray-300"
                      onClick={() => openModalClick(item.companyMemberId)}
                    >
                      <EditOutlinedIcon />
                    </button>

                    {selectedcompanyId === item.companyMemberId && showModal &&(
                      <MyStaffModal
                      onClose={closeModal}
                      companyId={item.companyId}
                      companymemberId={item.companyMemberId}
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
