import { useState, useEffect } from "react";
import axios from "axios";


export default function StaffDetailAxios(url:string) {
    const [staffLists, setStaffLists] = useState<any>(null);
   
  useEffect(() => {
    axios
      .get(url)
      .then((response) => {
        if (!response.data) {
          throw new Error("No data found");
        }
        setStaffLists(response.data);
       
      })
      .catch((err) => {
        console.log(err);
      });
  }, [url]);

  return [staffLists];
};
