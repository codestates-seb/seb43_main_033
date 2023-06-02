"use client";

import axios from "axios";
import { error } from "console";
import { useRouter } from "next/router";
import { ChangeEvent, Dispatch, SetStateAction, useState } from "react";

interface MemberData {
  name: string;
  phoneNumber: string;
  email: string;
  password: string;
  residentNumber: string;
  birthday: string;
  address: string;
}

export default function SignupFrom() {
  const [userData, setUserData] = useState<MemberData>({
    name: "",
    phoneNumber: "",
    email: "",
    password: "",
    residentNumber: "",
    birthday: "",
    address: "",
  });
  const [emailInfo, setEmailInfo] = useState("");
  const [phoneNumberInfo, setPhoneNumberInfo] = useState("");
  const [info, setInfo] = useState("");
  const [birthdayInfo, setBirthdayInfo] = useState("");
  const [residentNumberInfo, setResidentNumberInfo] = useState("");
  const [isSubmit, setIsSubmit] = useState(false);
  const router = useRouter();
  const emailRegex =
    /([\w-.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
  const regExp = /^[0-9]*$/;
  const handleChange = (
    e: ChangeEvent<HTMLInputElement>,
    field: keyof MemberData
  ) => {
    setUserData((prevMember) => ({
      ...prevMember,
      [field]: e.target.value,
    }));
  };
  const handleSubmit = () => {
    if (isSubmit) {
      axios
        .post<MemberData>(`${process.env.NEXT_PUBLIC_URL}/members`, userData)
        .then((res) => router.push("/login"))
        .catch((error) => {
          if (error.response.status === 409) {
            setInfo("이미 존재하는 email입니다.");
          }
        });
    }
  };

  const emailHandler = (e: any) => {
    handleChange(e, "email");
    if (!emailRegex.test(e.target.value)) {
      setEmailInfo("이메일 형식이 틀렸습니다");
      setIsSubmit(false);
    } else {
      setEmailInfo("");
      setIsSubmit(true);
    }
  };
  const phoneNumberHandler = (e: any) => {
    handleChange(e, "phoneNumber");
    if (
      (e.target.value.length !== 10 && e.target.value.length !== 11) ||
      !regExp.test(e.target.value)
    ) {
      setPhoneNumberInfo("10~11자리의 숫자를 입력해주세요");
      setIsSubmit(false);
    } else {
      setPhoneNumberInfo("");
      setIsSubmit(true);
    }
  };
  const birthdayHandler = (e: any) => {
    handleChange(e, "birthday");
    if (e.target.value.length !== 6 || !regExp.test(e.target.value)) {
      setBirthdayInfo("생년월일을 6자리로 입력해주세요");
      setIsSubmit(false);
    } else {
      setBirthdayInfo("");
      setIsSubmit(true);
    }
  };
  const residentNumberHandler = (e: any) => {
    handleChange(e, "residentNumber");
    if (e.target.value.length !== 7 || !regExp.test(e.target.value)) {
      setResidentNumberInfo("주민등록번호 뒷자리 7자리를 입력해주세요");
      setIsSubmit(false);
    } else {
      setResidentNumberInfo("");
      setIsSubmit(true);
    }
  };

  return (
    <div className="flex flex-col">
      <label htmlFor="name" className="font-semibold text-gray-700">
        이름
      </label>
      <input
        id="name"
        onChange={(e) => handleChange(e, "name")}
        className="w-[270px] outline-none border rounded-sm px-3 py-1 focus:border-green-500 mb-2"
      ></input>
      <label className="font-semibold text-gray-700" htmlFor="phoneNumber">
        전화번호
      </label>
      <input
        id="phoneNumber"
        onChange={(e) => phoneNumberHandler(e)}
        className="outline-none border rounded-sm px-3 py-1 focus:border-green-500 mb-2"
      ></input>
      <p className="text-red-400 text-[10px]">{phoneNumberInfo}</p>
      <label className="font-semibold text-gray-700" htmlFor="email">
        메일
      </label>
      <input
        id="email"
        onChange={(e) => emailHandler(e)}
        className="outline-none border rounded-sm px-3 py-1 focus:border-green-500 mb-2"
      ></input>
      <p className=" text-[10px] text-red-400">{emailInfo}</p>
      <label className="font-semibold text-gray-700" htmlFor="password">
        비밀번호
      </label>
      <input
        type="password"
        id="password"
        onChange={(e) => handleChange(e, "password")}
        className="outline-none border rounded-sm px-3 py-1 focus:border-green-500 mb-2"
      ></input>
      <p className="font-semibold text-gray-700">주민등록번호</p>
      <div className="flex">
        <input
          id="birthday"
          onChange={(e) => birthdayHandler(e)}
          className="outline-none border rounded-sm px-3 py-1 focus:border-green-500 mb-2 mr-2 w-[130px]"
        ></input>
        <p className=" text-[20px]">-</p>
        <input
          id="residentNumber"
          onChange={(e) => residentNumberHandler(e)}
          className="outline-none border rounded-sm px-3 py-1 focus:border-green-500 mb-2 ml-2  w-[130px]"
        ></input>
      </div>
      <p className="text-red-400 text-[10px]">{birthdayInfo}</p>
      <p className="text-red-400 text-[10px]">{residentNumberInfo}</p>
      <label className="font-semibold text-gray-700" htmlFor="address">
        주소
      </label>
      <input
        id="address"
        onChange={(e) => handleChange(e, "address")}
        className="outline-none border rounded-sm px-3 py-1 focus:border-green-500 mb-2"
      ></input>
      <p className="text-red-500">{info}</p>
      <button
        className="mt-10 bg-green-400 rounded-md py-2 text-white hover:bg-green-300"
        onClick={handleSubmit}
      >
        submit
      </button>
    </div>
  );
}
