import { useState } from "react";
import Bigsquare from "../../components/Bigsquare";
import CompanySearch from "../../components/CompanySearch";
import PaystubPreview from "../../components/PaystubPage/PaystubPreview";
import Navi from "../../components/WorkerNavi";

export default function Mypaystub(): JSX.Element {
  const [showModal, setShowModal] = useState(false);
  const isMyPaystub: boolean = true;
  const currentDate = new Date();
  const month = currentDate.getMonth() + 1;
  const year = currentDate.getFullYear();
  return (
    <div className="flex w-full">
      <Navi />
      <div className="w-full h-screen flex justify-center">
        <Bigsquare>
          <div className="bg-white p-3 m-5 flex justify-between">
            <div className="flex">
              <div>나의 급여명세서</div>
              <div className="ml-2">{`${year}년 ${month}월`}</div>
            </div>
            <button onClick={() => setShowModal(true)}>회사등록하기</button>
          </div>
          <div className="flex m-5">
            {showModal && <CompanySearch setShowModal={setShowModal} />}
            <PaystubPreview isMyPaystub={isMyPaystub} />
          </div>
        </Bigsquare>
      </div>
    </div>
  );
}
