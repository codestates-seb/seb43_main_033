"use client";
import { useState } from "react";
import BottomModal from "./BottomModal";

export default function BottomInformation() {
  const [isBottomModal, setIsBottomModal] = useState<boolean>(false);
  const [bottomList, setBottomList] = useState([
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
            className="mx-4 bg-stone-50 px-2 py-1 rounded-sm font-semibold drop-shadow cursor-pointer"
            onClick={handleOnBottomModal}
          >
            수정
          </span>
        </div>
        <section className="flex flex-col min-h-32">
          <ul className="flex flex-col overflow-scroll">
            {bottomList.map((x) => {
              return (
                <li className="flex flex-wrap justify-between">
                  <span>{x.input}</span>
                  <div>
                    <button
                      className="cursor-pointer"
                      onClick={() => handleOnDelete(x.key)}
                    >
                      취소
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
