//import { useState } from 'react';
"use client";
import { useState } from "react";
import Greenheader from "../../components/PaystubPage/GreenTop";
import StaffSelect from "../../components/StaffSelect";
import StaffSelectModal from "../../components/StaffSelectModal";
import PaystubPreview from "../../components/PaystubPage/PaystubPreview";
import WorkingStatus from "../../components/PaystubPage/WorkingStatus";
import Navi from "../../components/ManagerNavi";
import AccountAdd from "../../components/PaystubPage/AccountAdd";

const Paystub = () => {
  const [showModal, setShowModal] = useState(false);
  // const [selectedStaff, setSelectedStaff] = useState('');

  //  const [isChecked, setIsChecked] = useState<boolean>(false);
  return (
    <>
      <Navi />
      <div className="">
        <Greenheader>직원선택</Greenheader>
        <div className="bg-white p-3 m-5 flex justify-between">
          <StaffSelect>홍길동</StaffSelect>
          <button
            className="text-sm font-bold hover:bg-gray-300 px-2"
            onClick={() => setShowModal(true)}
          >
            직원선택하기
          </button>
        </div>
        {showModal && (
          <StaffSelectModal onClose={() => setShowModal(false)}>
            <StaffSelect>ddd</StaffSelect>
            <StaffSelect>ddd</StaffSelect>
            <StaffSelect>ddd</StaffSelect>
            <StaffSelect>ddd</StaffSelect>
            <StaffSelect>ddd</StaffSelect>
            <StaffSelect>ddd</StaffSelect>
            <StaffSelect>ddd</StaffSelect>
            <StaffSelect>ddd</StaffSelect>
          </StaffSelectModal>
        )}
        <Greenheader>계좌번호</Greenheader>
        <AccountAdd />
        <Greenheader>근태</Greenheader>
        <WorkingStatus></WorkingStatus>
        <Greenheader>지급내역</Greenheader>
        <PaystubPreview></PaystubPreview>
      </div>
    </>
  );
};

export default Paystub;
