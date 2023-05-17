"use client";
import { useState, useEffect } from "react";
import Image from "next/image";
import defaultcontract from "../public/defaultcontract.png";
import axios from "axios";
import Link from "next/link";

type ModalProps = {
  children: React.ReactNode;
  onClose: () => void;
};

interface MyStaffData {
  staffnumber: number;
  name: string;
  department: string;
  position: string;
  note: string;
  startTime: string;
  finishTime: string;
  grade: string;
  salary: number;
}
export default function StaffSelectModal({ onClose }: ModalProps) {
  /*const [stafflist] = staffAxios(
  `${process.env.REACT_APP_API_URL}/stafflist/${memberId}`
);


useEffect(() => {
  setStaffData(stafflist);
}, [stafflist]);*/

  const [staffData, setStaffData] = useState({
    staffnumber: "",
    membername: "",
    department: "",
    position: "",
    grade: "",
  });

  const staffInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setStaffData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const [selectedTab, setSelectedTab] = useState<string>("edit");

  const staffEditClick = () => {
  axios
    .patch(
      `http://localhost:8080/stafflists/1`,
      staffData,
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: localStorage.getItem("token"),
        },
      }
    )
    .then(() => {
      return <Link href="/mystaff" ></Link>;
    })
    .catch((err) => {
      console.log(err);
    });
};


  const staffDeleteClick= () => {
 
   axios
      .delete(`http://localhost:8080/stafflists/1`, {
        headers: {
          "Content-Type": "application/json",
          Authorization: localStorage.getItem("token"),
        },
      })
      .then(() => {
      })
      .catch((err) => {
        console.log(err);
      });
};

  //-------------------------------------------------
  const [file, setFile] = useState<File | null>(null);

  const [requestData, setRequestData] = useState({
    memberId: "",
    companyId: "",
    basicSalary: "",
    startOfContract: "",
    endOfContract: "",
    startTime: "",
    finishTime: "",
    information: "",
  });

  const contractlist = true;

  /* 
const [contractlist] = staffAxios(`http://localhost:8080/laborcontracts/1`);

useEffect(() => {
  setRequestData(contractlist);
}, [contractlist]);
*/

  const [contractEdit, setcontractEdit] = useState<boolean>(false);

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const selectedFile = e.target.files?.[0];
    setFile(selectedFile || null);
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setRequestData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const contractFormClick = () => {
    setcontractEdit(true);
  };

  const contractEditClick = () => {
    if (file) {
      const formData = new FormData();
      formData.append("file", file);
      formData.append("requestPart", JSON.stringify(requestData));

      axios
        .patch("http://localhost:8080/laborcontracts/1", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
            Authorization: localStorage.getItem("token"),
          },
        })
        .then(() => {
          setcontractEdit(false);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  };

  const contractDeleteClick = () => {
    axios
      .delete(`http://localhost:8080/laborcontracts/1`, {
        headers: {
          "Content-Type": "application/json",
          Authorization: localStorage.getItem("token"),
        },
      })
      .then(() => {
        setcontractEdit(false);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const contractSubmitClick = () => {
    if (file) {
      const formData = new FormData();
      formData.append("file", file);
      formData.append("requestPart", JSON.stringify(requestData));

      axios
        .post("http://localhost:8080/laborcontracts", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
            Authorization: localStorage.getItem("token"),
          },
        })
        .then(() => {
          setRequestData({
            memberId: "",
            companyId: "",
            basicSalary: "",
            startOfContract: "",
            endOfContract: "",
            startTime: "",
            finishTime: "",
            information: "",
          });
        })
        .catch((err) => {
          console.log(err);
        });
    }
  };

  return (
    <div className="fixed pt-40 z-10 inset-0 overflow-y-auto">
      <div className="flex items-center justify-center min-h-screen px-4">
        <div className="fixed inset-0 transition-opacity" aria-hidden="true">
          <div className="absolute inset-0 bg-gray-500 opacity-75"></div>
        </div>
        <div className="bg-white rounded-md z-10 w-full max-w-lg p-10 modal-content">
          <div className="flex justify-end">
            <button className="ml-5 fond-bold mb-3" onClick={onClose}>
              X
            </button>
          </div>

          <div className="flex justify-between mb-6">
            <button
              className={`tab-button ${
                selectedTab === "edit" ? "bg-emerald-300" : "bg-white"
              } p-5 px-10 rounded-md focus:outline-none hover:bg-emerald-200 transition-colors duration-300`}
              onClick={() => setSelectedTab("edit")}
            >
              사원 정보
            </button>

            <button
              className={`tab-button ${
                selectedTab === "contract" ? "bg-emerald-300" : "bg-white"
              } p-5 px-10 rounded-md focus:outline-none hover:bg-emerald-200 transition-colors duration-300`}
              onClick={() => setSelectedTab("contract")}
            >
              근로계약서
            </button>
          </div>

          {selectedTab === "edit" && (
            <div className="flex justify-between">
              <div>
                <div>
                  <div className="mt-2 mb-2 font-bold">사번</div>
                  <input
                    className="h-5 mb-2 border-b border-gray-300 focus:outline-none hover:outline-none"
                    type="text"
                    value={staffData.staffnumber}
                    onChange={staffInputChange}
                    name="staffnumber"
                  />
                </div>
                <div>
                  <div className="mb-2 font-bold">이름</div>
                  <input
                    className="mb-2 h-5 border-b border-gray-300 focus:outline-none hover:outline-none"
                    type="text"
                    value={staffData.membername}
                    onChange={staffInputChange}
                    name="membername"
                  />
                </div>
              </div>
              <div>
                <div>
                  <div className="mt-2 mb-1 font-bold">권리권한</div>
                  <input
                    className="h-5 mb-3 border-b border-gray-300 focus:outline-none hover:outline-none"
                    type="text"
                    value={staffData.grade}
                    onChange={staffInputChange}
                    name="grade"
                  />
                </div>
                <div>
                  <div className="mb-1 font-bold">부서</div>
                  <input
                    className="h-5 border-b border-gray-300 focus:outline-none hover:outline-none"
                    type="text"
                    value={staffData.position}
                    onChange={staffInputChange}
                    name="position"
                  />
                </div>
                <div className="modal-close pt-8 flex justify-end">
                  <button className="mr-3" onClick={() => staffEditClick()}>
                    submit
                  </button>
                  <button onClick={() => staffDeleteClick()}>Delete</button>
                </div>
              </div>
            </div>
          )}

          {selectedTab === "contract" && (
            <div>
              {contractlist ? (
                contractEdit ? (
                  <div className="flex justify-between">
                    <div>
                      {file ? (
                        <Image
                          className="w-40 h-30 mb-7"
                          src={URL.createObjectURL(file)}
                          alt="근로계약서"
                          width={40}
                          height={40}
                        />
                      ) : (
                        <Image
                          className="mb-7"
                          src={defaultcontract}
                          alt="근로계약서"
                        />
                      )}
                      <label
                        htmlFor="fileInput"
                        className="bg-gray-200 p-2 px-5 ml-2 mt-10 rounded-md text-sm hover:bg-gray-300"
                      >
                        업로드
                      </label>
                      <input
                        id="fileInput"
                        type="file"
                        style={{ display: "none" }}
                        onChange={handleFileChange}
                      />
                    </div>

                    <div>
                      <div>
                        <div className="ml-7 font-bold">기본급:</div>
                        <input
                          className="w-40 h-5 ml-7 pb-2 border-b border-gray-300 focus:outline-none hover:outline-none"
                          type="text"
                          name="basicSalary"
                          value={requestData.basicSalary}
                          onChange={handleInputChange}
                        />
                      </div>

                      <div>
                        <div className="mt-2 mb-2 ml-7 font-bold">계약기간</div>
                        <div className="flex items-center ml-7">
                          <input
                            className="flex-1 h-5 mr-2 border-b pr-3 border-gray-300 focus:outline-none hover:outline-none"
                            type="date"
                            name="startOfContract"
                            value={requestData.startOfContract}
                            onChange={handleInputChange}
                          />
                          <div>~</div>
                          <input
                            className="flex-1 h-5 ml-2 border-b border-gray-300 focus:outline-none hover:outline-none"
                            type="date"
                            name="endOfContract"
                            value={requestData.endOfContract}
                            onChange={handleInputChange}
                          />
                        </div>
                      </div>

                      <div>
                        <div className="mt-2 mb-2 ml-8 font-bold">근무시간</div>
                        <div className="flex items-center ml-8">
                          <input
                            className="flex-1 h-5 mr-2 border-b pr-3 border-gray-300 focus:outline-none hover:outline-none"
                            type="time"
                            name="startTime"
                            value={requestData.startTime}
                            onChange={handleInputChange}
                          />
                          <div>~</div>
                          <input
                            className="flex-1 h-5 ml-2 border-b border-gray-300 focus:outline-none hover:outline-none"
                            type="time"
                            name="finishTime"
                            value={requestData.finishTime}
                            onChange={handleInputChange}
                          />
                        </div>
                      </div>

                      <div>
                        <div className="ml-7 font-bold">근로계약서 정보</div>
                        <input
                          className="w-30 h-5 ml-7 pb-2 border-b border-gray-300 focus:outline-none hover:outline-none"
                          type="text"
                          name="information"
                          value={requestData.information}
                          onChange={handleInputChange}
                        />
                      </div>

                      <div className="modal-close pt-8 flex justify-end">
                        <button onClick={contractEditClick}>Editsubmit</button>
                      </div>
                    </div>
                  </div>
                ) : (
                  <div className="flex justify-between">
                    <div>
                      {file ? (
                        <Image
                          className="w-40 h-30 mb-7"
                          src={URL.createObjectURL(file)}
                          alt="근로계약서"
                          width={40}
                          height={40}
                        />
                      ) : (
                        <Image
                          className="w-40 h-30 mb-7"
                          src={defaultcontract}
                          alt="근로계약서"
                        />
                      )}
                      <label
                        htmlFor="fileInput"
                        className="bg-gray-200 p-2 px-5 ml-2 mt-10 rounded-md text-sm hover:bg-gray-300"
                      >
                        업로드
                      </label>
                      <input
                        id="fileInput"
                        type="file"
                        style={{ display: "none" }}
                        onChange={handleFileChange}
                      />
                    </div>

                    <div>
                      <div>
                        <div className="ml-7 font-bold">기본급:</div>
                        <div className="ml-7">10000000</div>
                      </div>

                      <div>
                        <div className="mt-2 mb-2 ml-7 font-bold">계약기간</div>
                        <div className="flex items-center ml-7">
                          <div>2023-05-02</div>
                          <div>~</div>
                          <div>2023-05-02</div>
                        </div>
                      </div>

                      <div>
                        <div className="mt-2 mb-2 ml-7 font-bold">근무시간</div>
                        <div className="flex items-center ml-7">
                          <div>9:00</div>
                          <div>~</div>
                          <div>18:00</div>
                        </div>
                      </div>

                      <div>
                        <div className="ml-7 font-bold">근로계약서 정보</div>
                        <div className="ml-7">xxxxxxxxxxx</div>
                      </div>

                      <div className="modal-close pt-8 flex justify-end">
                        <button onClick={contractFormClick}>Edit</button>
                        <button className="ml-3" onClick={contractDeleteClick}>
                          delete
                        </button>
                      </div>
                    </div>
                  </div>
                )
              ) : (
                <div className="flex justify-between">
                  <div>
                    {file ? (
                      <Image
                        className="w-40 h-30 mb-7"
                        src={URL.createObjectURL(file)}
                        alt="근로계약서"
                        width={40}
                        height={40}
                      />
                    ) : (
                      <Image
                        className="mb-7"
                        src={defaultcontract}
                        alt="근로계약서"
                      />
                    )}
                    <label
                      htmlFor="fileInput"
                      className="bg-gray-200 p-2 px-5 ml-2 mt-10 rounded-md text-sm hover:bg-gray-300"
                    >
                      업로드
                    </label>
                    <input
                      id="fileInput"
                      type="file"
                      style={{ display: "none" }}
                      onChange={handleFileChange}
                    />
                  </div>

                  <div>
                    <div>
                      <div className="ml-7 font-bold">기본급:</div>
                      <input
                        className="w-40 h-5 ml-7 pb-2 border-b border-gray-300 focus:outline-none hover:outline-none"
                        type="text"
                        name="basicSalary"
                        value={requestData.basicSalary}
                        onChange={handleInputChange}
                      />
                    </div>

                    <div>
                      <div className="mt-2 mb-2 ml-7 font-bold">계약기간</div>
                      <div className="flex items-center ml-7">
                        <input
                          className="flex-1 h-5 mr-2 border-b pr-3 border-gray-300 focus:outline-none hover:outline-none"
                          type="date"
                          name="startOfContract"
                          value={requestData.startOfContract}
                          onChange={handleInputChange}
                        />
                        <div>~</div>
                        <input
                          className="flex-1 h-5 ml-2 border-b border-gray-300 focus:outline-none hover:outline-none"
                          type="date"
                          name="endOfContract"
                          value={requestData.endOfContract}
                          onChange={handleInputChange}
                        />
                      </div>
                    </div>

                    <div>
                      <div className="mt-2 mb-2 ml-8 font-bold">근무시간</div>
                      <div className="flex items-center ml-8">
                        <input
                          className="flex-1 h-5 mr-2 border-b pr-3 border-gray-300 focus:outline-none hover:outline-none"
                          type="time"
                          name="startTime"
                          value={requestData.startTime}
                          onChange={handleInputChange}
                        />
                        <div>~</div>
                        <input
                          className="flex-1 h-5 ml-2 border-b border-gray-300 focus:outline-none hover:outline-none"
                          type="time"
                          name="finishTime"
                          value={requestData.finishTime}
                          onChange={handleInputChange}
                        />
                      </div>
                    </div>

                    <div>
                      <div className="ml-7 font-bold">근로계약서 정보</div>
                      <input
                        className="w-30 h-5 ml-7 pb-2 border-b border-gray-300 focus:outline-none hover:outline-none"
                        type="text"
                        name="information"
                        value={requestData.information}
                        onChange={handleInputChange}
                      />
                    </div>

                    <div className="modal-close pt-8 flex justify-end">
                      <button onClick={contractSubmitClick}>submit</button>
                    </div>
                  </div>
                </div>
              )}
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
