"use client";

import { useEffect, useState } from "react";
import AccountAdd from "./AccountAdd";
import axios from "axios";

export default function AccountList() {
  // const accountDataArr: Account[] = accountData.bank;
  const [accountAdd, setAccountAdd] = useState(false);
  const [accountEditId, setAccountEditId] = useState<number | null>(null);
  const [accountDeleteId, setAccountDeleteId] = useState<number | null>(null);
  const [accoutEdit, setAccountEdit] = useState<string | null>(null);
  const [accountData, setAccountData] = useState([]);
  useEffect(() => {
    const token = localStorage.getItem("token");
    axios
      .get(
        `http://ec2-3-39-22-248.ap-northeast-2.compute.amazonaws.com:8080/members/7`,
        {
          headers: {
            Authorization: token,
          },
        }
      )
      .then((res) => {
        setAccountData(res.data.bank);
      })
      .catch((err) => console.log(err));
  }, []);
  return (
    <div className="ml-10">
      {accountData.map((el, idx) => {
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
            <button
              className="text-[12px] text-gray-400"
              onClick={() => {
                setAccountEditId(el.memberBankId);
                setAccountEdit(el.accountNumber);
              }}
            >
              edit
            </button>
            <button
              className="ml-5 text-[12px] text-gray-400"
              onClick={() => setAccountDeleteId(el.memberBankId)}
            >
              delete
            </button>
            {accountEditId === el.memberBankId ? (
              <AccountAdd
                setAccountAdd={setAccountAdd}
                setAccountEditId={setAccountEditId}
                accountEditId={accountEditId}
                accoutEdit={accoutEdit}
              />
            ) : null}
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
  //계좌리스트더미데이터
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
      memberBankId: 2,
      bankId: 3,
      bankName: "난쟁은행",
      accountNumber: "1234-112-124421",
      bankCode: "005",
      mainAccount: false,
    },
    {
      memberBankId: 3,
      bankId: 3,
      bankName: "백설왕국은행",
      accountNumber: "156-112-124421",
      bankCode: "001",
      mainAccount: true,
    },
  ],
};

interface BankData {
  bankId: number;
  bankCode: string;
  bankName: string;
}

export const bankData = [
  {
    bankId: 2,
    bankCode: "003",
    bankName: "IBK기업은행",
  },
  {
    bankId: 6,
    bankCode: "012",
    bankName: "농협중앙회(단위농축협)",
  },
  {
    bankId: 4,
    bankCode: "007",
    bankName: "수협은행",
  },
  {
    bankId: 1,
    bankCode: "002",
    bankName: "KDB산업은행",
  },
  {
    bankId: 3,
    bankCode: "004",
    bankName: "KB국민은행",
  },
  {
    bankId: 5,
    bankCode: "011",
    bankName: "NH농협은행",
  },
  {
    bankId: 11,
    bankCode: "032",
    bankName: "부산은행",
  },
  {
    bankId: 7,
    bankCode: "020",
    bankName: "우리은행",
  },
  {
    bankId: 10,
    bankCode: "031",
    bankName: "대구은행",
  },
  {
    bankId: 9,
    bankCode: "027",
    bankName: "한국씨티은행",
  },
  {
    bankId: 8,
    bankCode: "023",
    bankName: "SC제일은행",
  },
  {
    bankId: 12,
    bankCode: "034",
    bankName: "광주은행",
  },
  {
    bankId: 13,
    bankCode: "035",
    bankName: "제주은행",
  },
  {
    bankId: 15,
    bankCode: "039",
    bankName: "경남은행",
  },
  {
    bankId: 16,
    bankCode: "045",
    bankName: "새마을금고중앙회",
  },
  {
    bankId: 17,
    bankCode: "048",
    bankName: "신협중앙회",
  },
  {
    bankId: 18,
    bankCode: "050",
    bankName: "저축은행중앙회",
  },
  {
    bankId: 14,
    bankCode: "037",
    bankName: "전북은행",
  },
  {
    bankId: 19,
    bankCode: "064",
    bankName: "산림조합중앙회",
  },
  {
    bankId: 22,
    bankCode: "088",
    bankName: "신한은행",
  },
  {
    bankId: 23,
    bankCode: "089",
    bankName: "케이뱅크",
  },
  {
    bankId: 20,
    bankCode: "071",
    bankName: "우체국",
  },
  {
    bankId: 21,
    bankCode: "081",
    bankName: "하나은행",
  },
  {
    bankId: 25,
    bankCode: "092",
    bankName: "토스뱅크",
  },
  {
    bankId: 24,
    bankCode: "090",
    bankName: "카카오뱅크",
  },
  {
    bankId: 26,
    bankCode: "218",
    bankName: "KB증권",
  },
  {
    bankId: 30,
    bankCode: "247",
    bankName: "NH투자증권",
  },
  {
    bankId: 27,
    bankCode: "238",
    bankName: "미래에셋대우",
  },
  {
    bankId: 29,
    bankCode: "243",
    bankName: "한국투자증권",
  },
  {
    bankId: 31,
    bankCode: "261",
    bankName: "교보증권",
  },
  {
    bankId: 28,
    bankCode: "240",
    bankName: "삼성증권",
  },
  {
    bankId: 32,
    bankCode: "262",
    bankName: "하이투자증권",
  },
  {
    bankId: 33,
    bankCode: "263",
    bankName: "현대차증권",
  },
  {
    bankId: 34,
    bankCode: "264",
    bankName: "키움증권",
  },
  {
    bankId: 35,
    bankCode: "265",
    bankName: "이베스트투자증권",
  },
  {
    bankId: 37,
    bankCode: "267",
    bankName: "대신증권",
  },
  {
    bankId: 36,
    bankCode: "266",
    bankName: "SK증권",
  },
  {
    bankId: 39,
    bankCode: "271",
    bankName: "토스증권",
  },
  {
    bankId: 38,
    bankCode: "269",
    bankName: "한화투자증권",
  },
  {
    bankId: 40,
    bankCode: "278",
    bankName: "신한금융투자",
  },
  {
    bankId: 41,
    bankCode: "279",
    bankName: "DB금융투자",
  },
  {
    bankId: 42,
    bankCode: "280",
    bankName: "유진투자증권",
  },
  {
    bankId: 43,
    bankCode: "287",
    bankName: "메리츠증권",
  },
];
