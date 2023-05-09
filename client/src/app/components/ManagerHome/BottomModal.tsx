"use client";

import { useState, Dispatch, SetStateAction } from "react";

interface BottomModalProps {
  handleOnBottomModal: () => void;
  bottomList: { key: number; input: string }[];
  setBottomList: Dispatch<SetStateAction<{ key: number; input: string }[]>>;
}

export default function BottomModal(props: BottomModalProps) {
  const { handleOnBottomModal, bottomList, setBottomList } = props;
  const [value, setValue] = useState<string>("");

  const handleOnSubmit = () => {
    setBottomList([...bottomList, { key: Date.now(), input: value }]);
    setValue("");
    handleOnBottomModal();
  };

  return (
    <article className="flex flex-col flex-wrap p-2 items-center w-1/2 min-h-8 z-10 top-1/2 inset-x-1/2 fixed -translate-y-1/2 -translate-x-1/2 bg-emerald-400 rounded drop-shadow-2xl">
      <button
        className="flex flex-nowrap justify-center items-center fixed right-0 top-0 w-5 h-5 bg-black rounded-sm"
        onClick={handleOnBottomModal}
      ></button>
      <div className="flex justify-center items-center font-semibold text-white mb-3">
        <span className="justify-center items-center font-semibold text-2xl">
          이번 달 특이사항
        </span>
      </div>
      <section className="flex flex-col p-3 pt-6 w-full bg-stone-100 rounded">
        <input
          type="text"
          className="border border-black border-solid rounded-sm break-words w-full resize-none overflow-auto"
          value={value}
          onChange={(e) => setValue(e.target.value)}
        />
        <div className="flex items-center justify-center mt-4 ">
          <button
            className="flex flex-wrap items-center justify-center w-10 bg-emerald-400 rounded drop-shadow-md cursor-pointer"
            onClick={handleOnSubmit}
          >
            등록
          </button>
        </div>
      </section>
    </article>
  );
}
