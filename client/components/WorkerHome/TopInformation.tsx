"use client";

import { useState, useEffect } from "react";
import Modal from "./Modal";
import TopInformationLi from "./TopInfo/TopInformationLi";
import axios from "axios";
import CompanySearch from "../CompanySearch";

interface Data {
  companyId: number | string;
  companyName: string;
  companySize: string;
  businessNumber: string;
  address: string;
  information: string;
}

interface InformationItem {
  label: string;
  description: string;
}

export default function TopInformation() {
  const [isModal, setIsModal] = useState<boolean>(false);
  const [showModal, setShowModal] = useState(false);
  const [isCompany, setIsCompany] = useState<boolean>(false);
  const [data, setData] = useState<Data>({
    companyId: "",
    companyName: "",
    companySize: "",
    businessNumber: "",
    address: "",
    information: "",
  });
  const patchInfo = () => {
    setIsModal(!isModal);
  };
  const isCompanyInfo = async () => {
    const memberid = localStorage.getItem("memberid");
    try {
      const response = await axios.get(
        `${process.env.NEXT_PUBLIC_URL}/members/${memberid}`
      );
      console.log(response.status);
      const id = response.data.companyMembers[0]?.companyId;
      if (id !== undefined) {
        setIsCompany(true);
        const companyResponse = await axios.get(
          `${process.env.NEXT_PUBLIC_URL}/companies/${id}`
        );
        const {
          companyId,
          companyName,
          companySize,
          businessNumber,
          address,
          information,
        } = companyResponse.data;
        setData({
          companyId,
          companyName,
          companySize,
          businessNumber,
          address,
          information,
        });
        console.log(companyResponse.data);
      } else {
        setIsCompany(false);
      }
    } catch (err) {
      console.log(err);
    }
  };

  const informationList: InformationItem[] = [
    {
      label: "법인명:",
      description: data.companyName,
    },
    {
      label: "기업 분류:",
      description: data.companySize,
    },
    {
      label: "사업자 등록번호:",
      description: data.businessNumber,
    },
    {
      label: "주소명:",
      description: data.address,
    },
    {
      label: "회사 정보:",
      description: data.information,
    },
  ];
  useEffect(() => {
    isCompanyInfo();
  }, []);
  return (
    <>
      {isModal && <Modal setData={setData} data={data} patchInfo={patchInfo} />}
      <div className="flex justify-between items-center bg-stone-200 p-2 mb-3 rounded">
        <span className="flex h-20 w-40 justify-center items-center p-1 border-black border-solid border rounded-sm">
          이미지
        </span>
        {isCompany && (
          <button
            className="mx-4 bg-stone-50 px-6 py-3 rounded font-semibold text-2xl drop-shadow hover:bg-slate-600 hover:text-white"
            onClick={patchInfo}
          >
            수정
          </button>
        )}
      </div>
      <section className="flex flex-col p-4 bg-stone-100 rounded">
        {isCompany ? (
          informationList.map((x) => {
            return (
              <TopInformationLi label={x.label} description={x.description} />
            );
          })
        ) : (
          <div>
            <div>회사 정보를 등록해주세요.</div>
            <button
              className=" font-semibold text-gray-500"
              onClick={() => setShowModal(true)}
            >
              회사등록하러가기 ➡️
            </button>
            {showModal && <CompanySearch setShowModal={setShowModal} />}
          </div>
        )}
      </section>
    </>
  );
}
