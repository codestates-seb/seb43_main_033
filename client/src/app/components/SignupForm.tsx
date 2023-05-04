import axios from "axios";
import { useState } from "react";

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
  const handleChange = (e, func) => {
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
    <div>
      <input
        placeholder="name"
        onChange={(e) => handleChange(e, setName)}
      ></input>
      <input
        placeholder="phoneNumber"
        onChange={(e) => handleChange(e, setPhoneNumber)}
      ></input>
      <input
        placeholder="email"
        onChange={(e) => handleChange(e, setEmail)}
      ></input>
      <input
        placeholder="password"
        onChange={(e) => handleChange(e, setPassword)}
      ></input>
      <input
        placeholder="residientNumber"
        onChange={(e) => handleChange(e, setResidientNumber)}
      ></input>
      <input
        placeholder="grade"
        onChange={(e) => handleChange(e, setGrade)}
      ></input>
      <input
        placeholder="address"
        onChange={(e) => handleChange(e, setAddress)}
      ></input>
      <button onClick={handleSubmit}>submit</button>
    </div>
  );
}
