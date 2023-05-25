import { Dispatch, SetStateAction, useEffect, useState } from "react";
import { CompanyData, CompanyMembers } from "../../pages/manager/paystub";

export function SelectCompany({
  companies,
  mycompanies,
  setCompanyId,
}: {
  companies: any;
  mycompanies: CompanyMembers[];
  setCompanyId: Dispatch<SetStateAction<number>>;
}) {
  const [filteredCompanies, setFilteredCompanies] = useState<any>([]);
  useEffect(() => {
    const companydata = companies.filter((company:any) => {
      return mycompanies.some(
        (mycompany) => company.companyId === mycompany.companyId
      );
    });
    setFilteredCompanies(companydata);
    companydata[0] && setCompanyId(companydata[0]["companyId"]);
  }, [companies, mycompanies]);
  const handleCompany = (e: any) => {
    setCompanyId(e.target.value);
  };
  return (
    <div className=" ml-10">
      <select onClick={handleCompany}>
        {filteredCompanies.map((el: any, idx: number) => {
          return (
            <option value={el.companyId} key={idx}>
              {el.companyName}
            </option>
          );
        })}
      </select>
    </div>
  );
}
