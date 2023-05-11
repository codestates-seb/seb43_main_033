"use client";

import { ChangeEvent, Dispatch, SetStateAction, useState } from "react";
import axios, { AxiosResponseHeaders } from "axios";

export default function LoginForm() {
  const [isLogin, setIsLogin] = useState(false);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const handleChange = (
    e: ChangeEvent<HTMLInputElement>,
    set: Dispatch<SetStateAction<string>>
  ) => {
    set(e.target.value);
  };
  const handleClick = () => {
    loginAxios();
  };
  const loginAxios = () => {
    axios
      .post("https://c49c-61-254-8-200.ngrok-free.app/login", {
        email,
        password,
      })
      .then((response) => {
        const token = response.headers.authorization;
        const saveToken = (token: string) => {
          localStorage.setItem("token", token);
        };
        const memberid = response.headers.memberid;
        const saveMemberId = (memberid: string) => {
          localStorage.setItem("memberid", memberid);
        };
        if (response.status === 200 || response.status === 201) {
          setIsLogin(false);
          saveToken(token);
          saveMemberId(memberid);
        }
      })
      .catch((err) => {
        console.log(err);
      });
  };
  return (
    <div>
      <label htmlFor="email" className="font-semibold text-gray-700">
        email
      </label>
      <input
        className="w-[270px] outline-none border rounded-sm px-3 py-1 focus:border-green-500 mb-2"
        id="email"
        onChange={(e) => handleChange(e, setEmail)}
      ></input>
      <label htmlFor="email" className="font-semibold text-gray-700">
        password
      </label>
      <input
        className="w-[270px] outline-none border rounded-sm px-3 py-1 focus:border-green-500 mb-2"
        id="password"
        onChange={(e) => handleChange(e, setPassword)}
      ></input>
      <button
        className="mt-10 w-full bg-green-400 rounded-md py-2 text-white hover:bg-green-300"
        onClick={handleClick}
      >
        submit
      </button>
    </div>
  );
}
