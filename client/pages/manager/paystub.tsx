//import { useState } from 'react';
"use client";
import { useEffect, useState } from "react";
import Greenheader from "../../components/PaystubPage/GreenTop";
import PaystubPreview from "../../components/PaystubPage/PaystubPreview";
import WorkingStatus from "../../components/PaystubPage/WorkingStatus";
import Navi from "../../components/ManagerNavi";
import AccountList from "../../components/PaystubPage/AccountList";
import axios from "axios";
import { SelectCompany } from "../../components/PaystubPage/SelectCompany";
import SelectCompanymembers from "../../components/PaystubPage/SelectCompanymembers";

const Paystub = () => {
  const [mycompanies, setMyCompanies] = useState<CompanyMembers[]>([]);
  const [companies, setCompanies] = useState<CompanyData[]>([]);
  const [companyId, setCompanyId] = useState(0);
  const [selectCompany, setSelectCompany] = useState<CompanyData | null>({});
  const [selectedCompanyMemberId, setSelectedCompanyMemberId] = useState(0);
  const [selectedMemberId, setSelectedMemberId] = useState(0);
  const [selectedCompanyMember, setSelectedCompanyMember] = useState("");

  useEffect(() => {
    const token = localStorage.getItem("token");
    const memberId = localStorage.getItem("memberid");
    {
      memberId &&
        axios
          .get(`${process.env.NEXT_PUBLIC_URL}/members/${memberId}`, {
            headers: {
              Authorization: token,
            },
          })
          .then((res) => {
            setMyCompanies(res.data.companyMembers);
          })
          .catch((err) => console.log(err));
    }
  }, []);

  useEffect(() => {
    const token = localStorage.getItem("token");
    axios
      .get(`${process.env.NEXT_PUBLIC_URL}/companies?page=1&size=200`, {
        headers: { Authorization: token },
      })
      .then((res) => {
        setCompanies(res.data.data);
      })
      .catch((err) => console.log(err));
  }, []);
  useEffect(() => {
    const selectCompanyData = companies.filter(
      (el) => el.companyId === companyId
    );
    selectCompanyData[0] && setSelectCompany(selectCompanyData[0]);
  }, [companyId]);
  return (
    <>
      <Navi />
      <div className="w-full p-10">
        <Greenheader>회사선택</Greenheader>
        <SelectCompany
          companies={companies}
          mycompanies={mycompanies}
          setCompanyId={setCompanyId}
        />
        <Greenheader>직원선택</Greenheader>
        <SelectCompanymembers
          selectCompany={selectCompany}
          setSelectedCompanyMember={setSelectedCompanyMember}
          setSelectedCompanyMemberId={setSelectedCompanyMemberId}
          setSelectedMemberId={setSelectedMemberId}
          selectedCompanyMemberId={selectedCompanyMemberId}          
          />
        <Greenheader>계좌번호</Greenheader>
        <AccountList
          selectedMemberId={selectedMemberId}
          selectedCompanyMemberId={selectedCompanyMemberId}
        />
        <Greenheader>근태</Greenheader>
        <WorkingStatus
          selectedCompanyMemberId={selectedCompanyMemberId}
          companyId={companyId}
        ></WorkingStatus>
        <Greenheader>지급내역</Greenheader>
        {selectedCompanyMemberId && companyId ? (
          <PaystubPreview
            companyId={companyId}
            selectedCompanyMemberId={selectedCompanyMemberId}
          ></PaystubPreview>
        ) : (
          ""
        )}
      </div>
    </>
  );
};

export default Paystub;

export interface CompanyMember {
  companyMemberId: number;
  companyId: number;
  memberId: number | null;
  name: string | null;
  grade: string;
  team: string;
  status: string | null;
  roles: string[] | null;
}

export interface CompanyData {
  companyId?: number;
  memberId?: number | null;
  companyName?: string;
  companySize?: string;
  businessNumber?: string;
  address?: string;
  information?: string;
  companyMembers?: CompanyMember[];
}

export interface CompanyMembers {
  companyMemberId: number;
  companyId: number;
}
