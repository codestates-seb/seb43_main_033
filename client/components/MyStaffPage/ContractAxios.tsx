import { useState, useEffect } from "react";
import axios from "axios";

export default function ContractAxios(url: string) {
  const [contractLists, setContractLists] = useState<any>(null);

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      axios
        .get(url, {
          headers: {
            Authorization: token,
          },
        })
        .then((response) => {
          if (!response.data) {
            throw new Error("No data found");
          }
          setContractLists(response.data);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }, [url]);

  return [contractLists, setContractLists];
};
