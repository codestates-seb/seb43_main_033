"use client";

import axios from "axios";
import { useEffect, useState } from "react";

interface Inputs {
  companyName: string;
  businessNumber: string;
  companySize: string;
  address: string;
  information: string;
}

export default function RegisterAuthenticatedModal({ prop, isModal }: any) {
  const [input, setInputs] = useState<Inputs>({
    companyName: "",
    businessNumber: prop.b_no,
    companySize: "",
    address: "",
    information: "",
  });
  const handleInputs = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setInputs({ ...input, [name]: value });
  };
  useEffect(() => console.log(input), [input]);
  const postCompanyInfo = async () => {
    const memberid = localStorage.getItem("memberid");
    const data = await axios
      .get(`${process.env.NEXT_PUBLIC_URL}/members/${memberid}`)
      .then((res) => {
        return res.data;
      });
    if (data.companyMembers[0] === undefined) {
      axios
        .post(`${process.env.NEXT_PUBLIC_URL}/companies`, input, {
          headers: {
            authorization: `${localStorage.getItem("token")}`,
          },
        })
        .then((res) => console.log(res.status))
        .catch((err) => console.log(err));
    } else if (data.companyMembers.length > 0) {
      alert("등록된 회사 정보가 존재하고 있습니다.");
    }
  };

  return (
    <article className="flex flex-col flex-wrap p-2 items-center w-1/2 z-10 top-1/2 inset-x-1/2 fixed -translate-y-1/2 -translate-x-1/2 bg-emerald-400 rounded drop-shadow-2xl">
      <button
        onClick={isModal}
        className="flex flex-nowrap justify-center items-center fixed right-0 top-0 w-5 h-5 bg-black rounded-sm"
      ></button>
      <div className="flex flex-1 justify-center items-center font-semibold text-white"></div>
      <section className="flex flex-col p-3 w-full bg-stone-100 rounded">
        <ul className="flex flex-col w-full">
          <li className="flex items-center mb-3">
            <label className="items-center w-36">회사 이름:</label>
            <input
              onChange={handleInputs}
              className="pl-2 w-full border border-slate-600 rounded"
              name="companyName"
            />
          </li>
          <li className="flex items-center mb-3">
            <label className="items-center w-36">사업자 등록번호:</label>
            <div className="w-full">{prop.b_no}</div>
          </li>
          <li className="flex items-center mb-3">
            <label className="items-center w-36">회사 규모:</label>
            <input
              onChange={handleInputs}
              className="pl-2 w-full border border-slate-600 rounded"
              name="companySize"
            />
          </li>
          <li className="flex items-center mb-3">
            <label className="items-center w-36">회사 주소:</label>
            <input
              onChange={handleInputs}
              className="pl-2 w-full border border-slate-600 rounded"
              name="address"
            />
          </li>
          <li className="flex items-center mb-3">
            <label className="items-center w-36">회사 정보:</label>
            <input
              onChange={handleInputs}
              className="pl-2 w-full border border-slate-600 rounded"
              name="information"
            />
          </li>
        </ul>
        <div className="flex justify-center items-center">
          <button
            onClick={() => {
              postCompanyInfo();
              isModal();
            }}
            className="mx-4 my-1 px-2 rounded-sm  bg-emerald-400 hover:bg-emerald-700 hover:text-white"
          >
            등록
          </button>
          <button
            onClick={isModal}
            className="mx-4 my-1 px-2 rounded-sm bg-emerald-400 hover:bg-emerald-700 hover:text-white"
          >
            취소
          </button>
        </div>
      </section>
    </article>
  );
}
