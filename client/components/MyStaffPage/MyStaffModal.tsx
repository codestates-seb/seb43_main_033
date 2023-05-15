"use client";
import { useState, useEffect } from "react";
import Image from "next/image";
import { useRouter } from "next/router";
import defaultcontract from "../../public/defaultcontract.png";
import axios from "axios";
import { format } from "date-fns";
import TabButton from "./TabButton";
import StaffInput from "./StaffInput";
import ContractInput from "./ContractInput";
import ContractTimeInput from "./ContractTimeInput";

type ModalProps = {
  onClose: () => void;
  selectedItemId: number;
};

interface StaffProps {
  id: number;
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

interface ContractsProps {
  laborcontractid: number;
  memberName: string;
  companyName: string;
  basicSalary: number;
  startOfContract: string;
  endOfContract: string;
  startTime: string;
  finishTime: string;
  information: string;
}

export default function MyStaffModal({ onClose, selectedItemId }: ModalProps) {
  /*const [stafflist] = staffAxios(
  `${process.env.REACT_APP_API_URL}/stafflist/${selectedItemId}`
);

useEffect(() => {
  setStaffData(stafflist);
}, [stafflist]);*/

  //더미 데이터 집어넣음
  const [staffData, setStaffData] = useState<StaffProps>({
    id: 1,
    staffnumber: 2023001,
    name: "홍길동",
    department: "회계팀",
    position: "사원",
    note: "지각",
    startTime: "00:00",
    finishTime: "00:00",
    grade: "standard",
    salary: 4000000,
  });

  const staffInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setStaffData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const [selectedTab, setSelectedTab] = useState<string>("edit");

  const router = useRouter();

  /*const staffEditClick = (selectedItemId) => {
  axios
    .patch(
      `http://localhost:8080/stafflists/${selectedItemId}`,
      staffData,
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: localStorage.getItem("token"),
        },
      }
    )
    .then(() => {
      router.push("work/mystaff");
    })
    .catch((err) => {
      console.log(err);
    });
};
*/

  /*const staffDeleteClick= (selectedItemId) => {
 
   axios
      .delete(`http://localhost:8080/stafflists/${selectedItemId}`, {
        headers: {
          "Content-Type": "application/json",
          Authorization: localStorage.getItem("token"),
        },
      })
      .then(() => {
        router.push("work/mystaff");
      })
      .catch((err) => {
        console.log(err);
      });
};*/

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

  //더미데이터 집어넣음
  const [contractlist, setContractlist] = useState<ContractsProps>({
    laborcontractid: 1,
    memberName: "홍길동",
    companyName: "회사 이름",
    basicSalary: 3000000,
    startOfContract: "2023-05-08T13:06:46.724",
    endOfContract: "2023-05-08T13:06:46.724",
    startTime: "00:00",
    finishTime: "00:00",
    information: "근로계약서 정보",
  });

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
        router.push("work/mystaff");
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

  const staffinformationList = [
    {
      label: "사번",
      value: staffData.staffnumber,
      onChange: staffInputChange,
      name: "staffnumber",
    },
    {
      label: "이름",
      value: staffData.name,
      onChange: staffInputChange,
      name: "name",
    },
    {
      label: "권리권한",
      value: staffData.grade,
      onChange: staffInputChange,
      name: "grade",
    },
    {
      label: "부서",
      value: staffData.position,
      onChange: staffInputChange,
      name: "position",
    },
  ];

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
            <TabButton
              selected={selectedTab === "edit"}
              onClick={() => setSelectedTab("edit")}
            >
              사원 정보
            </TabButton>
            <TabButton
              selected={selectedTab === "contract"}
              onClick={() => setSelectedTab("contract")}
            >
              근로계약서 정보
            </TabButton>
          </div>

          {selectedTab === "edit" && (
            <div className="flex justify-between">
              <div>
                <div className="grid grid-cols-2 gap-4">
                  {staffinformationList.map((information, index) => (
                    <div key={information.name}>
                      <StaffInput {...information} />
                    </div>
                  ))}
                </div>
              </div>
              <div>
                <div className="modal-close mt-5 pt-40 flex justify-end">
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
                        <ContractInput
                          label="기본급"
                          type="text"
                          name="basicSalary"
                          value={requestData.basicSalary}
                          onChange={handleInputChange}
                        />
                      </div>

                      <ContractTimeInput
                        label="계약기간"
                        type="date"
                        startTime={requestData.startOfContract}
                        finishTime={requestData.endOfContract}
                        onChange={handleInputChange}
                      />

                      <ContractTimeInput
                        label="근무시간"
                        type="time"
                        startTime={requestData.startTime}
                        finishTime={requestData.finishTime}
                        onChange={handleInputChange}
                      />

                      <ContractInput
                        label="근로계약서 정보"
                        type="text"
                        name="information"
                        value={requestData.information}
                        onChange={handleInputChange}
                      />

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
                        <div className="ml-7">{contractlist.basicSalary}</div>
                      </div>

                      <div>
                        <div className="mt-2 mb-2 ml-7 font-bold">계약기간</div>
                        <div className="flex items-center ml-7">
                          <div>
                            {format(
                              new Date(contractlist.startOfContract),
                              "yyyy-MM-dd"
                            )}
                          </div>
                          <div>~</div>
                          <div>
                            {format(
                              new Date(contractlist.endOfContract),
                              "yyyy-MM-dd"
                            )}
                          </div>
                        </div>
                      </div>

                      <div>
                        <div className="mt-2 mb-2 ml-7 font-bold">근무시간</div>
                        <div className="flex items-center ml-7">
                          <div>{contractlist.startTime}</div>
                          <div>~</div>
                          <div>{contractlist.finishTime}</div>
                        </div>
                      </div>

                      <div>
                        <div className="mt-2 ml-7 font-bold">
                          근로계약서 정보
                        </div>
                        <div className="mt-2 ml-7">
                          {contractlist.information}
                        </div>
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
                    <ContractInput
                      label="기본급:"
                      type="text"
                      name="basicSalary"
                      value={requestData.basicSalary}
                      onChange={handleInputChange}
                    />

                    <ContractTimeInput
                      label="계약기간"
                      type="date"
                      startTime={requestData.startOfContract}
                      finishTime={requestData.endOfContract}
                      onChange={handleInputChange}
                    />

                    <ContractTimeInput
                      label="근무시간"
                      type="time"
                      startTime={requestData.startTime}
                      finishTime={requestData.finishTime}
                      onChange={handleInputChange}
                    />

                    <ContractInput
                      label="근로계약서 정보"
                      type="text"
                      name="information"
                      value={requestData.information}
                      onChange={handleInputChange}
                    />

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
