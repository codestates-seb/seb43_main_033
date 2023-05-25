import { Dispatch, SetStateAction, useEffect, useState } from "react";
import StaffSelect from "../StaffSelect";
import StaffSelectModal from "../StaffSelectModal";
import axios from "axios";
import { CompanyData, CompanyMembers } from "../../pages/manager/paystub";

export default function SelectCompanymembers({
  selectCompany,
  setSelectedCompanyMember,
  setSelectedCompanyMemberId,
  setSelectedMemberId,
  selectedCompanyMemberId,
}: {
  selectCompany: any;
  setSelectedCompanyMember: any;
  setSelectedCompanyMemberId: any;
  setSelectedMemberId: any;
  selectedCompanyMemberId: any;
}) {
  const [showModal, setShowModal] = useState(false);
  const [nameArr, setNameArr] = useState<any>([]);
  const [approve, setApprove] = useState("pending");
  const [information, setInformation] = useState("");
  const [name, setName] = useState("");
  const [role, setRole] = useState("");
  useEffect(() => {
    selectCompany && setNameArr(selectCompany.companyMembers);
  }, [selectCompany]);
  const handleChange = (companymemberid: any, memberid: any, name: any) => {
    setSelectedCompanyMemberId(companymemberid);
    setSelectedMemberId(memberid);
    setName(name);
  };
  const handleSubmit = () => {
    setShowModal(false);
  };
  const handleSelectApprove = (e : any) => {
    setApprove(e.target.value);
  };
  const handleApprove = (companymembersid: any) => {
    const token = localStorage.getItem("token");
    axios
      .post(
        `${process.env.NEXT_PUBLIC_URL}/companymembers/pending/${companymembersid}/${approve}`,
        null,
        {
          headers: {
            Authorization: token,
          },
        }
      )
      .then(() => {
        setInformation("승인이 완료되었습니다");
      })
      .catch(() => {});
  };

  const handleSelectRoles = (companymemberid: number) => {
    const token = localStorage.getItem("token");
    axios
      .patch(
        `${process.env.NEXT_PUBLIC_URL}/companymembers/${companymemberid}`,
        {
          roles: [role],
        },
        {
          headers: {
            Authorization: token,
          },
        }
      )
      .then(() => {
        setInformation("권한변경이 완료되었습니다");
      })
      .catch(() => {});
  };

  return (
    <div className=" ml-10">
      <div className="flex">
        <div className=" p-2 bg-gray-200 w-fit">{name}</div>
        <button className="ml-5" onClick={() => setShowModal(true)}>
          직원선택하기
        </button>
      </div>
      {showModal && (
        <div className="ml-10">
          <div className="fixed pt-40 z-10 inset-0 overflow-y-auto">
            <div className="flex items-center justify-center min-h-screen px-4">
              <div
                className="fixed inset-0 transition-opacity"
                aria-hidden="true"
              >
                <div className="absolute inset-0 bg-gray-500 opacity-75"></div>
              </div>
              <div className="bg-white rounded-md z-10 w-full max-w-[500px] p-10 modal-content">
                <div className="flex justify-end">
                  <button
                    className="ml-5 fond-bold mb-3"
                    onClick={() => setShowModal(false)}
                  >
                    X
                  </button>
                </div>
                <div>
                  {nameArr && nameArr.length !== 0
                    ? nameArr.map((el: any, idx: number) => {
                        const [roles] = [el.roles];
                        return (
                          <div key={idx}>
                            <input
                              type="radio"
                              id={el.companyMemberId}
                              name="companymember"
                              onChange={() =>
                                handleChange(
                                  el.companyMemberId,
                                  el.memberId,
                                  el.name
                                )
                              }
                            ></input>
                            <label
                              className="ml-2"
                              htmlFor={el.companyMemberId}
                            >
                              {el.name}
                            </label>
                            <div className="flex items-end">
                              {el.status ? (
                                <div className=" text-gray-400 text-[10px] ">
                                  {el.status}
                                </div>
                              ) : (
                                <div className=" text-gray-400 text-[10px]">
                                  null
                                </div>
                              )}
                              <div>
                                <select
                                  className="text-[10px]"
                                  onChange={handleSelectApprove}
                                >
                                  <option value={"pending"}>pending</option>
                                  <option value={"approved"}>approved</option>
                                  <option value={"refuse"}>refuse</option>
                                </select>
                                <button
                                  onClick={() =>
                                    handleApprove(el.companyMemberId)
                                  }
                                  className="text-[10px]  bg-gray-200 rounded-sm"
                                >
                                  승인
                                </button>
                              </div>
                              <div className=" ml-2 text-gray-400 text-[10px] ">
                                {roles}
                              </div>
                              <div>
                                <select
                                  className="text-[10px]"
                                  onChange={(e) => setRole(e.target.value)}
                                >
                                  <option value={"MEMBER"}>Member</option>
                                  <option value={"MANAGER"}>Manager</option>
                                </select>
                                <button
                                  className="text-[10px]  bg-gray-200 rounded-sm"
                                  onClick={() =>
                                    handleSelectRoles(el.companyMemberId)
                                  }
                                >
                                  권한변경
                                </button>
                              </div>
                            </div>
                          </div>
                        );
                      })
                    : null}
                  <span className="text-[10px] text-red-500 ml-5">
                    {information}
                  </span>
                </div>
                <div>
                  <div className="flex justify-end">
                    <button
                      className="bg-green-300 py-1 px-2 rounded-md text-white text-[13px] hover:bg-green-500 mt-5"
                      onClick={handleSubmit}
                    >
                      submit
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
