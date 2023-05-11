"use client";

import { useState, useEffect } from "react";
import Modal from "./Modal";
import axios, { Axios } from "axios";

interface Data {
  image: string;
  companyName: string;
  companySize: string;
  businessNumber: string;
  address: string;
  information: string;
}

export default function TopInformation() {
  const [isModal, setIsModal] = useState(false);
  const [data, setData] = useState<Data>({
    image: "",
    companyName: "법인명 예시",
    companySize: "기업분류 예시",
    businessNumber: "사업자 등록번호 예시",
    address: "회사주소 예시",
    information: "회사정보 예시",
  });
  const patchInfo = () => {
    setIsModal(!isModal);
  };

  return (
    <>
      {isModal && <Modal setData={setData} data={data} patchInfo={patchInfo} />}
      <article className="flex flex-col flex-wrap min-h-32 bg-white p-6 mb-5 rounded drop-shadow">
        <div className="flex justify-between items-center bg-stone-200 p-2 mb-3 rounded">
          <span className="flex h-20 w-40 justify-center items-center p-1 border-black border-solid border rounded-sm">
            이미지
          </span>
          <button
            className="mx-4 bg-stone-50 px-6 py-3 rounded font-semibold text-2xl drop-shadow"
            onClick={patchInfo}
          >
            수정
          </button>
        </div>
        <section className="flex flex-col p-4 bg-stone-100 rounded">
          <div>
            <span className="flex">
              <label className="w-28">법인명: </label>
              <span>{data.companyName}</span>
            </span>
          </div>
          <div>
            <span className="flex">
              <label className="w-28">기업 분류: </label>
              <span>{data.companySize}</span>
            </span>
          </div>
          <div>
            <span className="flex">
              <label className="w-28">사업자 등록번호: </label>
              <span>{data.businessNumber}</span>
            </span>
          </div>
          <div>
            <span className="flex">
              <label className="w-28">주소명: </label>
              <span>{data.address}</span>
            </span>
          </div>
          <div>
            <span className="flex">
              <label className="w-28">회사 정보: </label>
              <span>{data.information}</span>
            </span>
          </div>
        </section>
      </article>
    </>
  );
}
