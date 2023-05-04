//import { useState } from 'react';
"use client"
import { useState } from 'react';
import GreenTop from '../components/GreenTop';
import StaffSelect from '../components/StaffSelect';
import StaffSelectModal from '../components/StaffSelectModal';
import PaystubName from '../components/PaystubName';
import PaystubInput from '../components/PaystubInput';
// import Navi from "../Navi"

const Paystub = () => {

  const [showModal, setShowModal] = useState(false); 
 // const [selectedStaff, setSelectedStaff] = useState('');


 


//  const [isChecked, setIsChecked] = useState<boolean>(false);
  return (
    <div>
      {/* <Navi /> */}
      <GreenTop>직원선택</GreenTop>
      <div className="bg-white p-3 m-5 flex justify-between">
      <StaffSelect>홍길동</StaffSelect>
        <button className="text-sm font-bold hover:bg-gray-300 px-2"  onClick={() => setShowModal(true)}>
          지난달 급여내역 불러오기
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
      <GreenTop>지급내역</GreenTop>
      <div className="bg-white p-3 m-5 flex-col ">
      <div className="flex">
      <PaystubName>기본금</PaystubName>
      <PaystubInput></PaystubInput>
      </div>
      <div className="flex">
      <PaystubName>통상시급</PaystubName>
      <PaystubInput></PaystubInput>
      </div>
      <div className="flex">
      <PaystubName>연장근로시간</PaystubName>
      <PaystubInput></PaystubInput>
      </div>
      <div className="flex">
      <PaystubName>휴일근무시간<p className="text-sm">(8시간 초과)</p></PaystubName>
      <PaystubInput></PaystubInput>
      </div>
      <div className="flex">
      <PaystubName>기타수당
      <button className='bg-white w-7 h-7 ml-5'>+</button>
      </PaystubName>
      <PaystubInput></PaystubInput>
      </div>
      

      </div>
      <GreenTop>공제내역</GreenTop>
      <div className="bg-white p-3 m-5 flex-col ">
        
      <div className="flex">
      <PaystubName>소득세</PaystubName>
      <PaystubInput></PaystubInput>
      </div>

      <div className="flex">
      <PaystubName>지방소득세</PaystubName>
      <PaystubInput></PaystubInput>
      </div>

      <div className="flex">
      <PaystubName>국민연금</PaystubName>
      <PaystubInput></PaystubInput>
      </div>

      <div className="flex">
      <PaystubName>고용보험</PaystubName>
      <PaystubInput></PaystubInput>
      </div>

      <div className="flex">
      <PaystubName>건강보험</PaystubName>
      <PaystubInput></PaystubInput>
      </div>

      </div>
    </div>
  );
};

export default Paystub;
