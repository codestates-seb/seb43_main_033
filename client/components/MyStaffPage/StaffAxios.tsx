import { useState, useEffect } from "react";
import axios from "axios";


export default function StaffAxios(url:string,page:number) {
    const [staffLists, setStaffLists] = useState<any>(null);
    const [list, setList] = useState(0); 
    

  useEffect(() => {
    
    axios
      .get(url)
      .then((response) => {
        if (!response.data) {
          throw new Error("No data found");
        }
        setStaffLists(response.data);
        setList(response.data.pageInfo.totalElements);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [url,page]);

  return [staffLists, list];
};
