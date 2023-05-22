//import { useState } from 'react';
// "use client";
import { useEffect, useState } from "react";
import Greenheader from "../../components/PaystubPage/GreenTop";
import StaffSelect from "../../components/StaffSelect";
import StaffSelectModal from "../../components/StaffSelectModal";
import PaystubPreview from "../../components/PaystubPage/PaystubPreview";
import WorkingStatus from "../../components/PaystubPage/WorkingStatus";
import Navi from "../../components/ManagerNavi";
import AccountAdd from "../../components/PaystubPage/AccountAdd";
import AccountList from "../../components/PaystubPage/AccountList";
import axios from "axios";

const Paystub = () => {
  const [showModal, setShowModal] = useState(false);
  // const [selectedStaff, setSelectedStaff] = useState('');

  //  const [isChecked, setIsChecked] = useState<boolean>(false);
  const [nameArr, setNameArr] = useState([]);
  useEffect(() => {
    axios
      .get(`${process.env.NEXT_PUBLIC_URL}/companies?page=1&size=200`)
      .then((res) => {
        setNameArr(res.data.data[4].companyMembers);
        console.log(res.data.data[4].companyMembers);
      })
      .catch((err) => console.log(err));
  }, []);

  console.log(nameArr);
  return (
    <>
      <Navi />
      <div className="w-full">
        <Greenheader>직원선택</Greenheader>
        <div className="bg-white p-3 m-5 flex justify-between">
          {nameArr &&
            nameArr.map((el, idx) => (
              <StaffSelect key={idx}>{el.member.name}</StaffSelect>
            ))}
          <button
            className="text-sm font-bold hover:bg-gray-300 px-2"
            onClick={() => setShowModal(true)}
          >
            직원선택하기
          </button>
        </div>
        {showModal && (
          <StaffSelectModal onClose={() => setShowModal(false)}>
            {nameArr &&
              nameArr.map((el, idx) => (
                <StaffSelect key={idx}>{el.member.name}</StaffSelect>
              ))}
          </StaffSelectModal>
        )}
        <Greenheader>계좌번호</Greenheader>
        <AccountList />
        <Greenheader>근태</Greenheader>
        <WorkingStatus></WorkingStatus>
        <Greenheader>지급내역</Greenheader>
        <PaystubPreview></PaystubPreview>
      </div>
    </>
  );
};

export default Paystub;
