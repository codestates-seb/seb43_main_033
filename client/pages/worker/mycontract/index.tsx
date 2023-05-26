"use client";
import axios from "axios";
import { useEffect, useState } from "react";
import Bigsquare from "../../../components/Bigsquare";
import Navi from "../../../components/WorkerNavi";

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

export default function Mycontract() {
  const [contractLists, setContractLists] = useState<any>(null);
  const [selectedContract, setSelectedContract] = useState<Contract | null>(
    null
  );

  const [contractUri, setContractUri] = useState<string>("");

  useEffect(() => {
    const fetchData = async () => {
      try {
        const token = localStorage.getItem("token");
        const response = await axios.get(
          `${process.env.NEXT_PUBLIC_URL}/worker/mycontract`,
          {
            headers: {
              Authorization: token,
            },
          }
        );
        setContractLists(response.data);
      } catch (error) {}
    };
    fetchData();
  }, []);

  const handleContractClick = (contract: Contract) => {
    setSelectedContract(contract);
    setContractUri(contract.uri);
  };

  return (
    <>
      <Navi />
      <div className="h-screen w-screen flex justify-center">
        <Bigsquare>
          <div className="bg-white p-3 m-5">
            <div>나의 계약</div>
          </div>
          <div>
            {contractLists && contractLists.length > 0 ? (
              <ul className="space-y-2">
                {contractLists.map((contract: Contract) => (
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
          </div>

          <div
            className="flex items-center"
            style={{ minHeight: "calc(100vh - 6rem)", overflow: "auto" }}
          >
            {selectedContract && (
              <div className="w-full h-full flex justify-center items-center">
                <img
                  src={contractUri}
                  alt="Contract Image"
                  
                />
              </div>
            )}
          </div>
        </Bigsquare>
      </div>
    </>
  );
}
