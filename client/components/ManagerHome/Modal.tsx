"use client";

import { useState, useEffect, Dispatch, SetStateAction } from "react";
import axios from "axios";

type props = {
  setData: Dispatch<
    SetStateAction<{
      image: string;
      companyName: string;
      companySize: string;
      businessNumber: string;
      address: string;
      information: string;
    }>
  >;
  data: {
    image: string;
    companyName: string;
    companySize: string;
    businessNumber: string;
    address: string;
    information: string;
  };
  patchInfo: () => void;
};

interface Inputs {
  image: string;
  companyName: string;
  companySize: string;
  businessNumber: string;
  address: string;
  information: string;
}

function Modal(props: any): React.ReactElement {
  const { setData, data, patchInfo } = props;
  const [inputs, setInputs] = useState<Inputs>({
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
    const { name, value } = e.target;
    setInputs({ ...inputs, [name]: value });
  };

  const handleOnSubmit = () => {
    setData({ ...data, ...inputs });
    axios.patch(
      `${process.env.NEXT_PUBLIC_URL}/companies/${data.companyId}`,
      {
        companyId: data.companyId,
        companyName: inputs.companyName,
        companySize: inputs.companySize,
        businessNumber: inputs.businessNumber,
        address: inputs.address,
        information: inputs.information,
      },
      {
        headers: {
          authorization: localStorage.getItem("token"),
        },
      }
    )
      .then((res) => console.log(res))
      .catch((err) => console.log(err));
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
      <div className="flex justify-between items-center font-semibold text-white mb-3">
        <span className="justify-center items-center font-semibold text-2xl">
          기업정보 수정
        </span>
      </div>
      <section className="flex-1 flex flex-col p-3 pt-6 w-full bg-stone-100 rounded">
        <article className="mb-8">
          <div className="flex items-center mb-3 ">
            <span className="w-28">회사명:</span>
            <input
              className="flex-1 pl-2 bg-stone-200 border border-slate-600 rounded-sm "
              onChange={handleOnChange}
              value={inputs.companyName}
              name="companyName"
            />
          </div>
          <div className="flex items-center mb-3">
            <span className="w-28">기업분류:</span>
            <input
              className="flex-1 pl-2 bg-stone-200 border border-slate-600 rounded-sm"
              onChange={handleOnChange}
              value={inputs.companySize}
              name="companySize"
            />
          </div>
          <div className="flex items-center mb-3">
            <span className="w-28">사업자 등록번호:</span>
            <input
              className="flex-1 pl-2 bg-stone-200 border border-slate-600 rounded-sm"
              onChange={handleOnChange}
              value={inputs.businessNumber}
              name="businessNumber"
            />
          </div>
          <div className="flex items-center mb-3">
            <span className="w-28">주소명:</span>
            <input
              className="flex-1 pl-2 bg-stone-200 border border-slate-600 rounded-sm"
              onChange={handleOnChange}
              value={inputs.address}
              name="address"
            />
          </div>
          <div className="flex items-center mb-3">
            <span className="w-28">기업 정보:</span>
            <input
              className="flex-1 pl-2 bg-stone-200 border border-slate-600 rounded-sm"
              type="text"
              onChange={handleOnChange}
              value={inputs.information}
              name="information"
            />
          </div>
        </article>
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
