"use client";

import { useState, useEffect, Dispatch, SetStateAction } from "react";

interface CompanyData {
  image: string;
  companyName: string;
  companySize: string;
  businessNumber: string;
  address: string;
  information: string;
}

interface Props {
  setData: (value: React.SetStateAction<CompanyData>) => void;
  data: CompanyData;
  patchInfo: () => void;
}

function Modal({ setData, data, patchInfo }: Props): React.ReactElement {
  const [inputs, setInputs] = useState<CompanyData>({
    image: "",
    companyName: "",
    companySize: "",
    businessNumber: "",
    address: "",
    information: "",
  });
  useEffect(() => setInputs({ ...data }), [data]);
  useEffect(() => console.log(inputs), [inputs]);
  const handleOnChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    console.log(e.target.name);
    const { name, value } = e.target;
    setInputs({ ...inputs, [name]: value });
  };

  const handleOnSubmit = () => {
    setData({ ...data, ...inputs });
    setInputs({
      image: "",
      companyName: "",
      companySize: "",
      businessNumber: "",
      address: "",
      information: "",
    });
    patchInfo();
  };

  return (
    <article className="flex flex-col flex-wrap p-2 items-center w-1/2 h-3/5 z-10 top-1/2 inset-x-1/2 fixed -translate-y-1/2 -translate-x-1/2 bg-emerald-400 rounded drop-shadow-2xl">
      <button
        className="flex flex-nowrap justify-center items-center fixed right-0 top-0 w-5 h-5 bg-black rounded-sm"
        onClick={patchInfo}
      ></button>
      <div className="flex flex-1 justify-center items-center font-semibold text-white mb-3">
        <span className="justify-center items-center font-semibold text-2xl">
          기업정보 수정
        </span>
      </div>
      <section className="flex flex-col p-3 pt-6 w-full bg-stone-100 rounded">
        <div className="flex items-center mb-1 ">
          <span className="w-28">회사명:</span>
          <input
            className="flex-1 pl-2 bg-stone-200 border border-slate-600 rounded-sm "
            onChange={handleOnChange}
            value={inputs.companyName}
            name="companyName"
          />
        </div>
        <div className="flex items-center mb-1">
          <span className="w-28">기업분류:</span>
          <input
            className="flex-1 pl-2 bg-stone-200 border border-slate-600 rounded-sm"
            onChange={handleOnChange}
            value={inputs.companySize}
            name="companySize"
          />
        </div>
        <div className="flex  items-center mb-1">
          <span className="w-28">사업자 등록번호:</span>
          <input
            className="flex-1 pl-2 bg-stone-200 border border-slate-600 rounded-sm"
            onChange={handleOnChange}
            value={inputs.businessNumber}
            name="businessNumber"
          />
        </div>
        <div className="flex items-center mb-1">
          <span className="w-28">주소명:</span>
          <input
            className="flex-1 pl-2 bg-stone-200 border border-slate-600 rounded-sm"
            onChange={handleOnChange}
            value={inputs.address}
            name="address"
          />
        </div>
        <div className="flex mb-1">
          <span className="w-28">기업 정보:</span>
          <input
            className="flex-1 pl-2 bg-stone-200 border border-slate-600 rounded-sm min-h-24"
            type="text"
            onChange={handleOnChange}
            value={inputs.information}
            name="information"
          />
        </div>
        <div className="flex justify-center items-center m-auto ">
          <button
            className="px-3 bg-emerald-400 rounded text-white drop-shadow-md"
            onClick={handleOnSubmit}
          >
            등록
          </button>
        </div>
      </section>
    </article>
  );
}
export default Modal;
