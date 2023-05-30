"use client";

import { ChangeEvent, Dispatch, SetStateAction, useState } from "react";
import axios, { AxiosResponseHeaders } from "axios";
import { useRouter } from "next/router";

export default function LoginForm() {
  const [isLogin, setIsLogin] = useState(false);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [companyId, setCompanyId] = useState(0);
  const [emailInfo, setEmailInfo] = useState("");
  const [passwordInfo, setPasswordInfo] = useState("");
  const [info, setInfo] = useState("");
  const router = useRouter();
  const emailRegex =
    /([\w-.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;


  const googleHandler = () => {
    window.location.assign(
      `${process.env.NEXT_PUBLIC_URL}/oauth2/authorization/google`
    );
  };


  const handleClick = () => {
    loginAxios();
  };
  const loginAxios = () => {
    if (emailRegex.test(email) && password.length >= 6) {
      axios
        .post(`${process.env.NEXT_PUBLIC_URL}/login`, {
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
          const refresh = response.headers.refresh;
          const saveRefresh = (refresh: string) => {
            localStorage.setItem("refresh", refresh);
          };

          if (response.status === 200 || response.status === 201) {
            setIsLogin(true);
            saveToken(token);
            saveMemberId(memberid);
            saveRefresh(refresh);
            window.location.href = "/";
          }
        })
        .catch((err) => {
          if (err.response.status === 401) {
            setInfo("아이디와 비밀번호를 다시 한번 확인해주세요");
          }
          setEmailInfo("");
          setPasswordInfo("");
        });
    }
  };

  const emailHandler = (e: any) => {
    setEmail(e.target.value);
    if (!emailRegex.test(e.target.value)) {
      setEmailInfo("이메일 형식이 틀렸습니다");
    } else {
      setEmailInfo("");
    }
  };
  const passwordHandler = (e: any) => {
    setPassword(e.target.value);
    if (e.target.value.length < 6) {
      setPasswordInfo("6글자 이상 입력하세요");
    } else {
      setPasswordInfo("");
    }
  };
  return (
    <div>
      <label htmlFor="email" className="font-semibold text-gray-700">
        email
      </label>
      <input
        className="w-[270px] outline-none border rounded-sm px-3 py-1 focus:border-green-500 mb-2"
        id="email"
        onChange={(e) => emailHandler(e)}
      ></input>
      <p className=" text-[10px] text-red-400">{emailInfo}</p>
      <label htmlFor="email" className="font-semibold text-gray-700">
        password
      </label>
      <input
        type="password"
        className="w-[270px] outline-none border rounded-sm px-3 py-1 focus:border-green-500 mb-2"
        id="password"
        onChange={(e) => passwordHandler(e)}
      ></input>
      <p className=" text-[10px] text-red-400">{passwordInfo}</p>
      <p className="text-sm text-red-400">{info}</p>
      <button
        className="mt-10 w-full bg-green-400 rounded-md py-2 text-white hover:bg-green-300"
        onClick={handleClick}
      >
        submit
      </button>
      <button
        onClick={googleHandler}
        className="w-full border-2 p-1 rounded-md mt-2"
      >
        Google 로그인
      </button>
    </div>
  );
}
