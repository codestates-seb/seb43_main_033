"use client";
import { useEffect, useState } from "react";
import BottomModal from "./BottomModal";

interface BottomItem {
  key: number;
  input: string;
}

export default function BottomInformation() {
  const [isBottomModal, setIsBottomModal] = useState(false);
  const [bottomList, setBottomList] = useState<BottomItem[]>([
    {
      key: 1683469475954,
      input: "안녕",
    },
    {
      key: 1683469475955,
      input: "후후",
    },
    {
      key: 1683469475956,
      input: "헤헤",
    },
    {
      key: 1683469475957,
      input: "하하",
    },
    {
      key: 1683469475958,
      input: "히히",
    },
  ]);

  const handleOnBottomModal = () => {
    setIsBottomModal(!isBottomModal);
  };
  const handleOnDelete = (key: number) => {
    const onClicked = bottomList.find((x) => x.key === key);
    // const idx = bottomList.indexOf(onClicked);
    setBottomList([...bottomList.filter((x) => x !== onClicked)]);
  };
  // useEffect(() => {
  //   axios
  //     .get("")
  //     .then((res) => setBottomList(res.data))
  //     .catch((err) => console.log(err));
  // }, []);
  // useEffect(() => {
  //   axios
  //     .post("")
  //     .then((res) => setBottomList(res.data))
  //     .catch((err) => console.log(err));
  // }, [bottomList]);

  return (
    <>
      {isBottomModal && (
        <BottomModal
          handleOnBottomModal={handleOnBottomModal}
          setBottomList={setBottomList}
          bottomList={bottomList}
        />
      )}
      <article className="flex flex-col flex-wrap min-h-32 bg-white p-6 mb-5 rounded drop-shadow">
        <div className="flex justify-between items-center bg-stone-200 p-2 mb-3 rounded">
          <span className="mx-4 font-semibold text-2xl">이번 달 특이사항</span>
          <span
            className="mx-4 bg-stone-50 px-2 py-1 rounded-sm font-semibold drop-shadow cursor-pointer  hover:bg-slate-600 hover:text-white"
            onClick={handleOnBottomModal}
          >
            수정
          </span>
        </div>
        <section className="flex flex-col min-h-32">
          <ul className="flex flex-col overflow-scroll">
            {bottomList.map((x) => {
              return (
                <li className="flex flex-wrap justify-between items-center">
                  <span>{x.input}</span>
                  <div>
                    <button
                      className="cursor-pointer hover:bg-stone-600 hover:text-white rounded group"
                      onClick={() => handleOnDelete(x.key)}
                    >
                      <svg
                        xmlns="http://www.w3.org/2000/svg"
                        className="w-8 y-8 group-hover:fill-white"
                        viewBox="0 96 960 960"
                      >
                        <path d="m249 849-42-42 231-231-231-231 42-42 231 231 231-231 42 42-231 231 231 231-42 42-231-231-231 231Z" />
                      </svg>
                    </button>
                  </div>
                </li>
              );
            })}
          </ul>
        </section>
      </article>
    </>
  );
}
