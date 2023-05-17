import { Dispatch, SetStateAction, useEffect, useState } from "react";

export default function AccountAdd({
  setAccountAdd,
  setAccountEditId,
  accountEditId,
  accoutEdit,
}: {
  setAccountAdd?: Dispatch<SetStateAction<boolean>> | undefined;
  setAccountEditId?: Dispatch<SetStateAction<number | null>> | undefined;
  accountEditId?: number | null;
  accoutEdit?: string | null;
}) {
  const [account, setAccount] = useState<string>("");
  useEffect(() => {
    accoutEdit ? setAccount(accoutEdit) : setAccount("");
  }, [accoutEdit]);
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
                onClick={
                  accountEditId
                    ? () => setAccountEditId && setAccountEditId(null)
                    : () => setAccountAdd && setAccountAdd(false)
                }
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
                value={account}
                onChange={(e) => setAccount(e.target.value)}
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
