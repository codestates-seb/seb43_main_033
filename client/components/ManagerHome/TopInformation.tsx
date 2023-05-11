"use client";

import { useState } from "react";
import Modal from "./Modal";
import TopInformationLi from "./TopInfo/TopInformationLi";

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
  const informationList = [
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
  // useEffect(() => {
  //   axios
  //     .get("")
  //     .then((res) => setData(res.data))
  //     .catch((err) => console.log(err));
  // }, []);

  // useEffect(() => {
  //   axios
  //     .post("")
  //     .then((res) => setData(res.data))
  //     .catch((err) => console.log(err));
  // }, [data]);
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
          {informationList.map((x) => {
            return (
              <TopInformationLi label={x.label} description={x.description} />
            );
          })}
        </section>
      </article>
    </>
  );
}
