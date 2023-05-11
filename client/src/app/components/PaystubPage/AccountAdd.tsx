import { useState } from "react";

export default function AccountAdd() {
  const [accountAdd, setAccountAdd] = useState(false);
  return (
    <div className="ml-10">
      {accountAdd ? (
        <div>
          <label htmlFor="bankName" className="mr-5 bg-gray-200 p-2 ">
            AccountNumber
          </label>
          <input
            id="bankName"
            className="borer border-b-2 outline-none"
          ></input>
          <button className="ml-[160px]">submit</button>
        </div>
      ) : null}
      <div className="flex justify-end">
        <button
          onClick={() => setAccountAdd((prev) => !prev)}
          className="bg-green-300 py-1 px-2 rounded-md text-white text-[13px] hover:bg-green-500 mt-5"
        >
          {accountAdd ? "Cancel" : "Add"}
        </button>
      </div>
    </div>
  );
}
