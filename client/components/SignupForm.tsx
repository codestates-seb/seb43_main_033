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
    <div className="flex flex-col">
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
      <label className="font-semibold text-gray-700" htmlFor="residientNumber">
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
  );
}
