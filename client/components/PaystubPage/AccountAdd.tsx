import { Dispatch, SetStateAction, useEffect, useState } from "react";
import { AccountSearch } from "./AccountSeach";
import { Account, bankData } from "./AccountList";
import axios from "axios";
import { useRouter } from "next/router";

export default function AccountAdd({
  setAccountAdd,
  setAccountEditId,
  accountEditId,
  accoutEdit,
  selectedCompanyMemberId,
  selectBankName,
  selectedMemberId,
}: {
  setAccountAdd?: Dispatch<SetStateAction<boolean>> | undefined;
  setAccountEditId?: Dispatch<SetStateAction<number | null>> | undefined;
  accountEditId?: number | null;
  accoutEdit?: string | null;
  selectedCompanyMemberId?: any;
  selectBankName?: string | null;
  selectedMemberId?: number | null;
}) {
  const [account, setAccount] = useState<string>("");
  const [bankId, setBankId] = useState<number | null>(null);
  const [mainAccount, setMainAccout] = useState(false);
  const [filteredBank, setFilteredBank] = useState<any>({});
  const [info, setInfo] = useState("");
  const router = useRouter();
  useEffect(() => {
    accoutEdit ? setAccount(accoutEdit) : setAccount("");
  }, [accoutEdit]);
  useEffect(() => {
    if (bankId) {
      const filteredbank: any = bankData.filter(
        (bank) => bank.bankId === bankId
      );
      setFilteredBank(filteredbank[0]);
    }
  }, [bankId]);
  const handleAccountSubmit = () => {
    const token = localStorage.getItem("token");
    const accountdata = {
      memberId: selectedMemberId,
      bankId: bankId,
      accountNumber: account,
      mainAccount,
    };
    axios
      .post(`${process.env.NEXT_PUBLIC_URL}/memberbanks`, accountdata, {
        headers: {
          Authorization: token,
        },
      })
      .then((res) => {
        setAccountAdd && setAccountAdd(false);
        router.reload();
      })
      .catch((err) => {
        if (err.response.status === 500) {
          setInfo("주계좌를 먼저 선택해주세요");
        }
        console.log(err);
      });
  };
  const handleAccountPatch = () => {
    const token = localStorage.getItem("token");
    const accountdata = {
      memberId: selectedCompanyMemberId,
      memberBankId: accountEditId,
      bankId: bankId,
      accountNumber: account,
      mainAccount,
    };
    axios
      .patch(
        `${process.env.NEXT_PUBLIC_URL}/memberbanks/${accountEditId}`,
        accountdata,
        {
          headers: {
            Authorization: token,
          },
        }
      )
      .then(() => {
        setAccountEditId && setAccountEditId(0);
      })
      .catch((err) => console.log(err));
  };
  return (
    <div className="ml-10">
      <div className="fixed pt-40 z-10 inset-0 overflow-y-auto">
        <div className="flex items-center justify-center min-h-screen px-4">
          <div className="fixed inset-0 transition-opacity" aria-hidden="true">
            <div className="absolute inset-0 bg-gray-500 opacity-75"></div>
          </div>
          <div className="bg-white rounded-md z-10 w-full max-w-[500px] p-10 modal-content">
            <div className="flex justify-end">
              <button
                className="ml-5 fond-bold mb-3"
                onClick={
                  accountEditId
                    ? () => setAccountEditId && setAccountEditId(null)
                    : () => setAccountAdd && setAccountAdd(false)
                }
              >
                X
              </button>
            </div>
            <div>
              <div>
                <AccountSearch bankId={bankId} setBankId={setBankId} />
                <p className="text-red-400">{info}</p>
              </div>
              <div className="my-5 flex">
                <div className="mr-5 bg-gray-200 p-2 w-fit">bankName</div>
                <div>
                  {Object.keys(filteredBank).length
                    ? filteredBank["bankName"]
                    : selectBankName}
                </div>
              </div>
              <label htmlFor="accountNumber" className="mr-5 bg-gray-200 p-2 ">
                AccountNumber
              </label>
              <input
                id="accountNumber"
                className="borer border-b-2 outline-none"
                value={account}
                onChange={(e) => setAccount(e.target.value)}
              ></input>
              <div className="flex  items-center mt-5">
                <div className=" w-fit mr-5 bg-gray-200 px-2 py-1 ">
                  MainAccount
                </div>
                <input
                  name="mainaccount"
                  type="radio"
                  id="mainAccount"
                  onClick={() => setMainAccout(true)}
                ></input>
                <label className="ml-2" htmlFor="mainAccount">
                  주계좌
                </label>
                <input
                  name="mainaccount"
                  type="radio"
                  id="notmainAccount"
                  className="ml-2"
                  defaultChecked
                  onClick={() => setMainAccout(false)}
                ></input>
                <label className="ml-2" htmlFor="notmainAccount">
                  일반계좌
                </label>
              </div>
              <div className="flex justify-end">
                <button
                  onClick={
                    accountEditId ? handleAccountPatch : handleAccountSubmit
                  }
                  className="bg-green-300 py-1 px-2 rounded-md text-white text-[13px] hover:bg-green-500 mt-5"
                >
                  submit
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
