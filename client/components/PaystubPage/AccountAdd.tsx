import { Dispatch, SetStateAction, useState } from "react";

export default function AccountAdd({
  setAccountAdd,
}: {
  setAccountAdd: Dispatch<SetStateAction<boolean>>;
}) {
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
                onClick={() => setAccountAdd(false)}
              >
                X
              </button>
            </div>
            <div>
              <label htmlFor="bankName" className="mr-5 bg-gray-200 p-2 ">
                AccountNumber
              </label>
              <input
                id="bankName"
                className="borer border-b-2 outline-none"
              ></input>
              <div className="flex justify-end">
                <button className="bg-green-300 py-1 px-2 rounded-md text-white text-[13px] hover:bg-green-500 mt-5">
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
