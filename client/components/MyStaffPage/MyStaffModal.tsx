import { useState, useEffect } from "react";
import Image from "next/image";
import { useRouter } from "next/router";
import defaultcontract from "../../public/defaultcontract.png";
import axios from "axios";
import { format } from "date-fns";
import TabButton from "./TabButton";
import StaffInput from "./StaffInput";
import ContractInput from "./ContractInput";
import StaffDetailAxios from "./StaffDetailAxios";
import ContractAxios from "./ContractAxios";

type ModalProps = {
  onClose: () => void;
  companyId: number;
  companymemberId: number;
};

type Staff = {
  companyMemberId: number;
  companyId: number;
  memberId: number;
  name: string;
  grade: string;
  team: string;
  status: string;
  roles: null;
};

type ContractRegistrationData = {
  basicSalary: number;
  startOfContract: string;
  endOfContract: string;
  startTime: string;
  finishTime: string;
  information: string;
};

type Contract = {
  laborContactId: number;
  memberName: string;
  companyName: string;
  bankName: string;
  accountNumber: string;
  accountHolder: string;
  basicSalary: number;
  startOfContract: string;
  endOfContract: string;
  startTime: string;
  finishTime: string;
  information: string;
  uri: string;
};


export default function MyStaffModal({
  
  onClose,
  companyId,
  companymemberId
}: ModalProps) {
  const [staffList] = StaffDetailAxios(
    `${process.env.NEXT_PUBLIC_URL}/companymembers/${companymemberId}`
  );

  const [staffData, setStaffData] = useState<Staff | null>(null);

  useEffect(() => {
    if (staffList) {
      setStaffData(staffList);
    }
  }, [staffList]);

  const staffInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setStaffData((prevData: Staff | null) => {
      if (prevData === null) {
        return null;
      }
      return {
        ...prevData,
        [name]: value,
      };
    });
  };

  const [selectedTab, setSelectedTab] = useState<string>("edit");

  const router = useRouter();

  const staffEditClick = (companymemberId: number) => {
    axios
      .patch(
        `${process.env.NEXT_PUBLIC_URL}/companymembers/${companymemberId}`,
        staffData,

        {
          headers: {
            "Content-Type": "application/json",
            Authorization: localStorage.getItem("token"),
          },
        }
      )
      .then(() => {
        onClose();
        router.reload();
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const staffDeleteClick = (companymemberId: number) => {

    axios
      .delete(
        `${process.env.NEXT_PUBLIC_URL}/companymembers/${companymemberId}`,
        {
          headers: {
            Authorization: localStorage.getItem("token"),
          },
        }
      )
      .then(() => {
        onClose();
      })
      .catch((err) => {
        console.log(err);
      });
  };

  //-------------------------------------------------
  const [file, setFile] = useState<File | null>(null);

  const defaultRequestData: ContractRegistrationData = {
    basicSalary: 0,
    startOfContract: "",
    endOfContract: "",
    startTime: "",
    finishTime: "",
    information: "",
  };

  const [requestData, setRequestData] =
    useState<ContractRegistrationData>(defaultRequestData);

  const [selectedContract, setSelectedContract] = useState<Contract | null>(
    null
  );

  const [laborcontractId, setLaborcontractId] = useState<number>();
  const [contractUri, setContractUri] = useState<string>("");

  const [contractList] = ContractAxios(
    `${process.env.NEXT_PUBLIC_URL}/manager/laborcontracts/${companymemberId}`
  );

  useEffect(() => {
    if (selectedContract !== null) {
      setRequestData(selectedContract);
    }
  }, [selectedContract]);

  const handleContractClick = (contract: Contract) => {
    setSelectedContract(contract);
    setLaborcontractId(contract.laborContactId);
    setContractUri(contract.uri);
  };

  const [contractEdit, setcontractEdit] = useState<boolean>(false);

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const selectedFile = e.target.files?.[0];
    if (selectedFile) {
      const modifiedFile = new File([selectedFile], selectedFile.name, {
        type: "image/png",
      });
      setFile(modifiedFile);
      setContractUri(URL.createObjectURL(modifiedFile));
    } else {
      setFile(null);
      setContractUri("");
    }
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setRequestData((prevData: ContractRegistrationData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const contractFormClick = () => {
    setcontractEdit(true);
  };

  const contractEditClick = (companyId: number, laborcontractId: number) => {


    const formData = new FormData();
    if (file) {
      formData.append("file", file);
    }
    formData.append(
      "requestPart",
      new Blob([JSON.stringify(requestData)], {
        type: "application/json",
      })
    );

    axios
      .patch(
        `${process.env.NEXT_PUBLIC_URL}/manager/${companyId}/laborcontracts/${laborcontractId}`,
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
            Authorization: localStorage.getItem("token"),
          },
        }
      )
      .then(() => {
        onClose();
        setcontractEdit(false);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const contractDeleteClick = (laborcontractId: number) => {
    axios
      .delete(
        `${process.env.NEXT_PUBLIC_URL}/laborcontracts/${laborcontractId}`,
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: localStorage.getItem("token"),
          },
        }
      )
      .then(() => {
        setcontractEdit(false);
        router.reload();
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const contractSubmitClick = (companyId: number, companymemberId: number) => {
    if (file) {
      const formData = new FormData();
      formData.append("file", file);
      formData.append(
        "requestPart",
        new Blob([JSON.stringify(requestData)], {
          type: "application/json",
        })
      );

      axios
        .post(
          `${process.env.NEXT_PUBLIC_URL}/manager/${companyId}/members/${companymemberId}/laborcontracts`,
          formData,
          {
            headers: {
              "Content-Type": "multipart/form-data;charset=UTF-8",
              Authorization: `${localStorage.getItem("token")}`,
            },
          }
        )
        .then(() => {
          onClose();
        })
        .catch((err) => {
          console.log(err);
        });
    }
  };

  const staffinformationList = [
    {
      label: "직급",
      value: staffData?.grade || "",
      onChange: staffInputChange,
      name: "grade",
    },
    {
      label: "부서",
      value: staffData?.team || "",
      onChange: staffInputChange,
      name: "team",
    },
    {
      label: "권한",
      value: staffData?.roles || "",
      onChange: staffInputChange,
      name: "roles",
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
                <div className="flex justify-between">
                  <div className="ml-10 mb-10">
                    <div className="mt-2 ml-5 mb-2 pl-3 font-bold">사번</div>
                    <div className="h-5 w-20 ml-5 mb-2 pl-3  focus:outline-none hover:outline-none">
                      {staffData?.companyMemberId}
                    </div>
                  </div>
                  <div className="ml-10 mb-10">
                    <div className="mt-2 ml-5 mb-2 pl-3 font-bold">이름</div>
                    <div className="h-5 w-20 ml-5 mb-2 pl-3  focus:outline-none hover:outline-none">
                      {staffData?.name}
                    </div>
                  </div>
                </div>

                <div className="grid grid-cols-2 gap-4">
                  {staffinformationList.map((information, index) => (
                    <div key={information.name}>
                      <StaffInput {...information} />
                    </div>
                  ))}
                 
                </div>
              </div>
              <div>
                <div className="modal-close mt-40 pt-40 flex justify-end">
                  <button
                    className="mr-3"
                    onClick={() => staffEditClick(companymemberId)}
                  >
                    submit
                  </button>
                  <button onClick={() => staffDeleteClick(companymemberId)}>
                    Delete
                  </button>
                </div>
              </div>
            </div>
          )}

          {selectedTab === "contract" && (
            <div>
              {contractList && contractList.length > 0 ? (
                <ul className="space-y-2 mb-10 ">
                  {contractList.map((contract: Contract) => (
                    <li
                      key={contract.laborContactId}
                      className="cursor-pointer border border-gray-300 p-2"
                      onClick={() => handleContractClick(contract)}
                    >
                      {contract.companyName}
                    </li>
                  ))}
                </ul>
              ) : (
                <div></div>
              )}

              {selectedContract ? (
                contractEdit ? (
                  <div className="flex justify-between">
                    <div>
                      {contractUri ? (
                        <Image
                          className="w-40 h-30 mb-7"
                          src={contractUri}
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
                          value={requestData?.basicSalary ?? ""}
                          onChange={handleInputChange}
                        />
                      </div>

                      <div>
                        <div className="mt-2 mb-2 ml-8 font-bold">근무시간</div>
                        <div className="flex items-center ml-8">
                          <input
                            className="flex-1 h-5 mr-2 border-b pr-3 border-gray-300 focus:outline-none hover:outline-none"
                            type="time"
                            name="startTime"
                            value={requestData?.startTime ?? ""}
                            onChange={handleInputChange}
                          />
                          <div>~</div>
                          <input
                            className="flex-1 h-5 ml-2 border-b border-gray-300 focus:outline-none hover:outline-none"
                            type="time"
                            name="finishTime"
                            value={requestData?.finishTime ?? ""}
                            onChange={handleInputChange}
                          />
                        </div>
                      </div>

                      <div>
                        <div className="mt-2 mb-2 ml-8 font-bold">계약기간</div>
                        <div className="flex items-center ml-8">
                          <input
                            className="flex-1 h-5 mr-2 border-b pr-3 border-gray-300 focus:outline-none hover:outline-none"
                            type="date"
                            name="startOfContract"
                            value={requestData?.startOfContract ?? ""}
                            onChange={handleInputChange}
                          />
                          <div>~</div>
                          <input
                            className="flex-1 h-5 ml-2 border-b border-gray-300 focus:outline-none hover:outline-none"
                            type="date"
                            name="endOfContract"
                            value={requestData?.endOfContract ?? ""}
                            onChange={handleInputChange}
                          />
                        </div>
                      </div>

                      <ContractInput
                        label="근로계약서 정보"
                        type="text"
                        name="information"
                        value={requestData?.information ?? ""}
                        onChange={handleInputChange}
                      />

                      <div className="modal-close pt-8 flex justify-end">
                        <button
                          onClick={() =>
                            laborcontractId &&
                            contractEditClick(companyId, laborcontractId)
                          }
                        >
                          update
                        </button>
                      </div>
                    </div>
                  </div>
                ) : (
                  <div className="flex justify-between">
                    <div>
                      {contractUri ? (
                        <Image
                          className="w-40 h-30 mb-7"
                          src={contractUri}
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
                        <div className="ml-7">
                          {selectedContract.basicSalary}
                        </div>
                      </div>

                      <div>
                        <div className="mt-2 mb-2 ml-7 font-bold">계약기간</div>
                        <div className="flex items-center ml-7">
                          <div>
                            {format(
                              new Date(selectedContract.startOfContract),
                              "yyyy-MM-dd"
                            )}
                          </div>
                          <div>~</div>
                          <div>
                            {format(
                              new Date(selectedContract.endOfContract),
                              "yyyy-MM-dd"
                            )}
                          </div>
                        </div>
                      </div>

                      <div>
                        <div className="mt-2 mb-2 ml-7 font-bold">근무시간</div>
                        <div className="flex items-center ml-7">
                          <div>{selectedContract.startTime}</div>
                          <div>~</div>
                          <div>{selectedContract.finishTime}</div>
                        </div>
                      </div>

                      <div>
                        <div className="mt-2 ml-7 font-bold">
                          근로계약서 정보
                        </div>
                        <div className="mt-2 ml-7">
                          {selectedContract.information}
                        </div>
                      </div>

                      <div className="modal-close pt-8 flex justify-end">
                        <button onClick={contractFormClick}>Edit</button>

                        <button
                          className="ml-5 pl-3"
                          onClick={() =>
                            laborcontractId &&
                            contractDeleteClick(laborcontractId)
                          }
                        >
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
                      value={requestData?.basicSalary ?? ""}
                      onChange={handleInputChange}
                    />

                    <div>
                      <div className="mt-2 mb-2 ml-8 font-bold">계약기간</div>
                      <div className="flex items-center ml-8">
                        <input
                          className="flex-1 h-5 mr-2 border-b pr-3 border-gray-300 focus:outline-none hover:outline-none"
                          type="date"
                          name="startOfContract"
                          value={requestData?.startOfContract ?? ""}
                          onChange={handleInputChange}
                        />
                        <div>~</div>
                        <input
                          className="flex-1 h-5 ml-2 border-b border-gray-300 focus:outline-none hover:outline-none"
                          type="date"
                          name="endOfContract"
                          value={requestData?.endOfContract ?? ""}
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
                          value={requestData?.startTime ?? ""}
                          onChange={handleInputChange}
                        />
                        <div>~</div>
                        <input
                          className="flex-1 h-5 ml-2 border-b border-gray-300 focus:outline-none hover:outline-none"
                          type="time"
                          name="finishTime"
                          value={requestData?.finishTime ?? ""}
                          onChange={handleInputChange}
                        />
                      </div>
                    </div>

                    <ContractInput
                      label="근로계약서 정보"
                      type="text"
                      name="information"
                      value={requestData?.information ?? ""}
                      onChange={handleInputChange}
                    />

                    <div className="modal-close pt-8 flex justify-end">
                      <button
                        onClick={() =>
                          contractSubmitClick(companyId, companymemberId)
                        }
                      >
                        submit
                      </button>
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
