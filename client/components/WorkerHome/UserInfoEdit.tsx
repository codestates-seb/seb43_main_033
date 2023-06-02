import axios from "axios";
import { useEffect, useState } from "react";
import UserInfoEditInput from "./UserInfoEditInput";

export default function UserInfoEdit({
  setEdit,
  info,
}: {
  setEdit: any;
  info: any;
}) {
  const [name, setName] = useState("");
  const [phoneNumber, setPhonNumber] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [address, setAddress] = useState("");
  const [birthday, setBirthday] = useState("");
  const [residentNumber, setResidentNumber] = useState("");
  const [birthdayInfo, setBirthdayInfo] = useState("");
  const [residentNumberInfo, setresidentNumberInfo] = useState("");
  const dataArr = [
    { value: name || "", setValue: setName, dataName: "이름" },
    { value: phoneNumber || "", setValue: setPhonNumber, dataName: "전화번호" },
    { value: email || "", setValue: setEmail, dataName: "메일" },
    { value: password || "", setValue: setPassword, dataName: "비밀번호" },
    { value: address || "", setValue: setAddress, dataName: "주소" },
  ];

  const regExp = /^[0-9]*$/;
  const emailRegex =
    /([\w-.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
  useEffect(() => {
    if (info.length !== 0) {
      setName(info.name);
      setPhonNumber(info.phoneNumber);
      setPassword(info.password);
      setAddress(info.address);
      setBirthday(info.birthday);
    }
  }, [info]);
  const memberEdit = () => {
    const memberid = localStorage.getItem("memberid");
    const token = localStorage.getItem("token");
    const body: any = {
      name,
      address,
    };
    if (password !== "") {
      body.password = password;
    }
    if (email !== "" && emailRegex.test(email)) {
      body.email = email;
    }
    if (
      phoneNumber !== "" &&
      regExp.test(phoneNumber) &&
      (phoneNumber.length === 10 || phoneNumber.length === 11)
    ) {
      body.phoneNumber = phoneNumber;
    }
    if (birthday !== "" && regExp.test(birthday) && birthday.length === 6) {
      body.birthday = birthday;
    }
    if (
      residentNumber !== "" &&
      regExp.test(residentNumber) &&
      residentNumber.length === 6
    ) {
      body.residentNumber = residentNumber;
    }
    axios
      .patch(`${process.env.NEXT_PUBLIC_URL}/members/${memberid}`, body, {
        headers: {
          Authorization: token,
        },
      })
      .then((res) => {
        window.location.href = "/worker";
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const birthdayHandler = (e: any) => {
    setBirthday(e.target.value);
    if (
      (!regExp.test(e.target.value) || e.target.value.length !== 6) &&
      e.target.value !== ""
    ) {
      setBirthdayInfo("생년월일을 6자리로 입력해주세요");
    } else {
      setBirthdayInfo("");
    }
  };
  const residentNumberHandler = (e: any) => {
    setResidentNumber(e.target.value);
    if (
      (!regExp.test(e.target.value) || e.target.value.length !== 7) &&
      e.target.value !== ""
    ) {
      setresidentNumberInfo("주민등록번호 뒷자리 7자리를 입력해주세요");
    } else {
      setresidentNumberInfo("");
    }
  };
  return (
    <div className="fixed pt-40 z-10 inset-0 overflow-y-auto">
      <div className="flex items-center justify-center min-h-screen px-4">
        <div className="fixed inset-0 transition-opacity" aria-hidden="true">
          <div className="absolute inset-0 bg-gray-500 opacity-75"></div>
        </div>
        <div className="bg-white rounded-md z-10 w-full max-w-[600px] p-10 modal-content">
          <div className="flex justify-end">
            <button
              className="ml-5 fond-bold mb-3"
              onClick={() => {
                setEdit(false);
              }}
            >
              X
            </button>
          </div>
          <div className="flex flex-col">
            {dataArr.map((el, idx) => (
              <div key={idx}>
                <UserInfoEditInput
                  value={el.value}
                  setValue={el.setValue}
                  dataName={el.dataName}
                />
              </div>
            ))}
          </div>
          <div className="mt-3 w-full flex">
            <div className="w-[170px]  mt-1">
              <div className="mr-5 p-2 w-96">주민등록번호</div>
            </div>
            <input
              placeholder="앞자리"
              className="borer border-b-2 outline-none w-[90px]"
              value={birthday}
              onChange={(e) => birthdayHandler(e)}
            ></input>
            <div className="flex items-center">
              <p className="text-[20px] mx-2 text-gray-600">-</p>
            </div>
            <input
              placeholder="뒷자리"
              className="borer border-b-2 outline-none w-[90px]"
              value={residentNumber}
              onChange={(e) => residentNumberHandler(e)}
            ></input>

            <div>
              <p className="text-red-400 text-[10px]">{birthdayInfo}</p>
              <p className="text-red-400 text-[10px]">{residentNumberInfo}</p>
            </div>
          </div>
          <div className="flex justify-end text-gray-500 hover:text-gray-300">
            <button onClick={memberEdit}>submit</button>
          </div>
        </div>
      </div>
    </div>
  );
}
