import { Dispatch, SetStateAction, useState } from "react";
import { BankData, bankData } from "./AccountList";

export function AccountSearch({
  bankId,
  setBankId,
}: {
  bankId?: number | null;
  setBankId?: Dispatch<SetStateAction<number | null>>;
}) {
  const [bankSearch, setBankSearch] = useState("");
  const [bankSearchBar, setBankSearchBar] = useState(false);
  const [filteredBank, setFilteredBank] = useState<BankData[]>([]);

  const handleSerach = (e: any) => {
    setBankSearch(e.target.value);
    const filterData = bankData.filter((el) =>
      el.bankName.includes(e.target.value)
    );
    setFilteredBank(filterData);
  };
  const handleClickBank = (id: number) => {
    setBankSearch("");
    setBankId && setBankId(id);
  };
  return (
    <div>
      <button onClick={() => setBankSearchBar((prev) => !prev)}>
        🔎 search
      </button>
      <input
        className="ml-2 px-2 py-1 border border-gray-300  outline-offset-2 outline-blue-300"
        id="bankname"
        value={bankSearch}
        onChange={handleSerach}
      ></input>
      {bankSearch.length !== 0 ? (
        <div className="fixed bg-gray-200 w-[400px] h-min-[200px] ">
          {filteredBank.map((el, idx) => {
            return (
              <div key={el.bankId}>
                <div
                  className="flex hover:bg-gray-100"
                  onClick={() => handleClickBank(el.bankId)}
                >
                  <div className="m-2">{el.bankCode}</div>
                  <div className="m-2">{el.bankName}</div>
                </div>
              </div>
            );
          })}
        </div>
      ) : null}
    </div>
  );
}
