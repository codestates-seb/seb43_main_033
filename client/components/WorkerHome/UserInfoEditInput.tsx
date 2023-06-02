import { useState } from "react";

export default function UserInfoEditInput({
  value,
  setValue,
  dataName,
}: {
  value: any;
  setValue: any;
  dataName: string;
}) {
  const [emailInfo, setEmailInfo] = useState("");
  const [phoneNumberInfo, setPhoneNumberInfo] = useState("");
  const emailRegex =
    /([\w-.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
  const regExp = /^[0-9]*$/;
  const inputHandler = (e: any, dataName: string) => {
    setValue(e.target.value);
    if (dataName === "메일") {
      if (!emailRegex.test(e.target.value) && e.target.value !== "") {
        setEmailInfo("이메일 형식이 틀렸습니다");
      } else {
        setEmailInfo("");
      }
    }
    if (dataName === "전화번호") {
      if (
        (e.target.value.length !== 10 &&
          e.target.value.length !== 11 &&
          e.target.value !== "") ||
        !regExp.test(e.target.value)
      ) {
        setPhoneNumberInfo("10~11자리의 숫자를 입력해주세요");
      } else {
        setPhoneNumberInfo("");
      }
    }
  };
  return (
    <div className="mt-3 w-full flex">
      <div className="w-[170px]  mt-1">
        <label htmlFor={`${value}`} className="mr-5 p-2 w-96">
          {`${dataName}`}
        </label>
      </div>
      <input
        type={dataName === "비밀번호" ? "password" : ""}
        id={`${value}`}
        className=" border-b-2 outline-none w-[200px]"
        value={value}
        onChange={(e) => inputHandler(e, dataName)}
      ></input>
      {dataName === "메일" ? (
        <p className="text-red-400 text-[10px]">{emailInfo}</p>
      ) : null}
      {dataName === "전화번호" ? (
        <p className="text-red-400 text-[10px]">{phoneNumberInfo}</p>
      ) : null}
    </div>
  );
}
