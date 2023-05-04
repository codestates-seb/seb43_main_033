"use client"
import { useState } from 'react';
import Bigsquare from '../components/Bigsquare';
import ListBox from '../components/ListBox';
import MyStaffModal from '../components/MyStaffModal';
import Navi from "../Navi"

export default function Mystaff() {

  const [showModal, setShowModal] = useState(false); 
  return (
    <div className="flex flex-col ">
      <Navi />
      <div className="flex justify-end pb-3">
        <h1 className="pr-10">우리 회사의 근무시간 : 09:00~18:00</h1>
        <h1 className="mr-8">오늘의 근무상황</h1>
      </div>
      <div className="h-screen w-screen flex justify-center">
        <Bigsquare>
            <div className="bg-white p-3 m-5 flex justify-between">
                <div>나의 직원들</div>
                <div>2023.05.03</div>
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
            <ListBox>
            <div>2023001</div>
            <div>홍길동</div>
            <div>회계팀</div>
            <div>사원</div>
            <div>16:00 퇴근</div>
            <div>09:00~18:00</div>
            <div className="pr-5">standard</div>
            <button className="text-sm font-bold hover:bg-gray-300" onClick={() => setShowModal(true)}>수정</button>
            {showModal && (
  <MyStaffModal onClose={() => setShowModal(false)}>

  </MyStaffModal>
)}
             </ListBox>
        </Bigsquare>
      </div>
    </div>
  );
}
