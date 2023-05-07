"use client";

import { useState } from "react";
import axios from "axios";

export default function login() {
  const [isLogin, setIsLogin] = useState(false);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const handleChange = (e, set) => {
    set(e.target.value);
  };
  const handleClick = () => {
    loginAxios();
  };
  const loginAxios = () => {
    // axios.defaults.withCredentials = true;
    axios
      .post(
        "https://c49c-61-254-8-200.ngrok-free.app/login",
        {
          email,
          password,
        }
        // {
        //   headers: {
        //     "Content-Type": "application/json",
        //     // "Access-Control-Allow-Origin": "*",
        //     // "Access-Control-Allow-Headers":
        //     //   "Origin, X-Requested-With, Content-Type, Accept",
        //     "Access-Control-Expose-Headers": "Authorization",
        //     // "ngrok-skip-browser-warning": "69420",
        //   },
        // }
      )
      .then((response) => {
        // 백엔드가 헤더에 담아서 보내준 토큰을 가져옴
        const token = response.headers.authorization;
        // 로컬에 토큰을 저장하는 함수 매개변수로 토큰을 받음
        const saveToken = (token) => {
          localStorage.setItem("token", token);
        };

        // 멤버id(유저id)도 헤더에 담긴 값을 가져옴
        const memberid = response.headers.memberid;
        // 로컬에 저장하는 함수 매개변수로 id를 받음
        const saveMemberId = (memberid) => {
          localStorage.setItem("memberid", memberid);
        };

        //응답 성공코드가 올때 실행
        if (response.status === 200 || response.status === 201) {
          /*  로그인 성공시
          => 로그인 상태 변경, 토큰과 아이디 로컬에 저장, 디스패치로 Nav와 Footer보이게 true설정, 네비게이트로 페이지 이동 */
          setIsLogin(false);
          saveToken(token);
          saveMemberId(memberid);
        }
      })
      .catch((err) => {
        /*응답이 안될때 로그인 상태 변경 / 콘솔 오류코드*/
        console.log(err);
      });
  };
  return (
    <div className="w-screen h-screen">
    <main key="login" className="flex justify-center">
      <div className="flex justify-center">
        <input
          placeholder="email"
          onChange={(e) => handleChange(e, setEmail)}
        ></input>
        <input
          placeholder="password"
          onChange={(e) => handleChange(e, setPassword)}
        ></input>
        <button onClick={handleClick}>submit</button>
      </div>
    </main>
    </div>
  );
}
