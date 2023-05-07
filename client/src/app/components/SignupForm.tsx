"use client";

import axios from "axios";
import { ChangeEvent, Dispatch, SetStateAction, useState } from "react";

interface MemberData {
  name: string;
  phoneNumber: string;
  email: string;
  password: string;
  residientNumber: string;
  grade: string;
  address: string;
}

export default function SignupFrom() {
  const [name, setName] = useState<string>("");
  const [phoneNumber, setPhoneNumber] = useState<string>("");
  const [email, setEmail] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [residientNumber, setResidientNumber] = useState<string>("");
  const [grade, setGrade] = useState<string>("");
  const [address, setAddress] = useState<string>("");
  const handleChange = (
    e: ChangeEvent<HTMLInputElement>,
    func: Dispatch<SetStateAction<string>>
  ) => {
    func(e.target.value);
  };
  const handleSubmit = () => {
    let memberdata: MemberData = {
      name,
      phoneNumber,
      email,
      password,
      residientNumber,
      grade,
      address,
    };
    console.log(memberdata);
    axios
      .post<MemberData>(
        "https://c49c-61-254-8-200.ngrok-free.app/members",
        memberdata
      )
      .then((res) => console.log(res))
      .catch((err) => console.log(err));
  };
  return (
    <div className="flex justify-center m-0 py-20 w-screen items-center  bg-gray-50">
      <div className="p-5">
        <h1 className="text-[24px] font-semibold mb-10">
          모두의 급여에서 간편하게 월급을 확인하세요
        </h1>
        <ul>
          <li className="mb-5 text-gray-500">
            급여명세서 전송여부를 메일로 알려드립니다
          </li>
          <li className="mb-5 text-gray-500">
            오늘의 근무상황을 체크할 수 있습니다
          </li>
          <li className="mb-5 text-gray-500">
            근로계약서를 확인할 수 있습니다
          </li>
        </ul>
      </div>
      <div className="flex flex-col m-0 shadow-lg shadow-slate-200 rounded-lg py-12 px-6 bg-white">
        <label htmlFor="name" className="font-semibold text-gray-700">
          name
        </label>
        <input
          id="name"
          onChange={(e) => handleChange(e, setName)}
          className="w-[270px] outline-none border rounded-sm px-3 py-1 focus:border-green-500 mb-2"
        ></input>
        <label className="font-semibold text-gray-700" htmlFor="phoneNumber">
          phonenumber
        </label>
        <input
          id="phoneNumber"
          onChange={(e) => handleChange(e, setPhoneNumber)}
          className="outline-none border rounded-sm px-3 py-1 focus:border-green-500 mb-2"
        ></input>
        <label className="font-semibold text-gray-700" htmlFor="email">
          email
        </label>
        <input
          id="email"
          onChange={(e) => handleChange(e, setEmail)}
          className="outline-none border rounded-sm px-3 py-1 focus:border-green-500 mb-2"
        ></input>
        <label className="font-semibold text-gray-700" htmlFor="password">
          password
        </label>
        <input
          id="password"
          onChange={(e) => handleChange(e, setPassword)}
          className="outline-none border rounded-sm px-3 py-1 focus:border-green-500 mb-2"
        ></input>
        <label
          className="font-semibold text-gray-700"
          htmlFor="residientNumber"
        >
          residientnumber
        </label>
        <input
          id="residientNumber"
          onChange={(e) => handleChange(e, setResidientNumber)}
          className="outline-none border rounded-sm px-3 py-1 focus:border-green-500 mb-2"
        ></input>
        <label className="font-semibold text-gray-700" htmlFor="grade">
          grade
        </label>
        <input
          id="grade"
          onChange={(e) => handleChange(e, setGrade)}
          className="outline-none border rounded-sm px-3 py-1 focus:border-green-500 mb-2"
        ></input>
        <label className="font-semibold text-gray-700" htmlFor="address">
          address
        </label>
        <input
          id="address"
          onChange={(e) => handleChange(e, setAddress)}
          className="outline-none border rounded-sm px-3 py-1 focus:border-green-500 mb-2"
        ></input>
        <button
          className="mt-10 bg-green-400 rounded-md py-2 text-white hover:bg-green-300"
          onClick={handleSubmit}
        >
          submit
        </button>
      </div>
    </div>
  );
}
