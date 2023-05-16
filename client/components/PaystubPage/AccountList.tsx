import { useState } from "react";
import AccountAdd from "./AccountAdd";

export default function AccountList() {
  const accountDataArr: Account[] = accountData.bank;
  const [accountAdd, setAccountAdd] = useState(false);
  return (
    <div className="ml-10">
      {accountDataArr.map((el, idx) => {
        return (
          <div
            key={idx}
            className={`flex mt-5 px-2 py-1 w-[500px] ${
              el.mainAccount ? "bg-gray-200" : "bg-white"
            }`}
          >
            <div className="w-[120px]">{el.bankCode}</div>
            <div className="w-[170px]">{el.bankName}</div>
            <div className="w-[170px]">{el.accountNumber}</div>
          </div>
        );
      })}
      <div className="flex justify-end">
        <button
          onClick={() => setAccountAdd((prev) => !prev)}
          className="bg-green-300 py-1 px-2 rounded-md text-white text-[13px] hover:bg-green-500 mt-5"
        >
          {accountAdd ? "Cancel" : "Add"}
        </button>
      </div>
      {accountAdd ? <AccountAdd setAccountAdd={setAccountAdd} /> : null}
    </div>
  );
}

interface Account {
  memberBankId: number;
  bankId: number;
  bankName: string;
  accountNumber: string;
  bankCode: string;
  mainAccount: boolean;
}

interface AccountData {
  bank: Account[];
}

export const accountData: AccountData = {
  bank: [
    {
      memberBankId: 1,
      bankId: 3,
      bankName: "KB국민은행",
      accountNumber: "1123-112-124421",
      bankCode: "004",
      mainAccount: false,
    },
    {
      memberBankId: 1,
      bankId: 3,
      bankName: "난쟁은행",
      accountNumber: "1234-112-124421",
      bankCode: "005",
      mainAccount: false,
    },
    {
      memberBankId: 1,
      bankId: 3,
      bankName: "백설왕국은행",
      accountNumber: "156-112-124421",
      bankCode: "001",
      mainAccount: true,
    },
  ],
};
