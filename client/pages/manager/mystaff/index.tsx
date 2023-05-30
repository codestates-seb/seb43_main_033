"use client";
import Bigsquare from "../../../components/Bigsquare";
import ListBox from "../../../components/ListBox";
import MyStaffModal from "../../../components/MyStaffPage/MyStaffModal";
import Navi from "../../../components/ManagerNavi";
import { format } from "date-fns";
import EditOutlinedIcon from "@mui/icons-material/EditOutlined";
import StaffAxios from "../../../components/MyStaffPage/StaffAxios";
import axios from "axios";
import { SelectCompany } from "../../../components/PaystubPage/SelectCompany";
import { useEffect, useState } from "react";
import { CompanyData, CompanyMembers } from "../paystub";
import Pagination from "../../../components/MyStaffPage/Pagination";

type Staff = {
  companyMemberId: number;
  companyId: number;
  memberId: number;
  name: string;
  grade: string;
  team: string;
  status: string;
  roles: null;
};

export default function Mystaff() {
  const [mycompanies, setMyCompanies] = useState<CompanyMembers[]>([]);
  const [companies, setCompanies] = useState<CompanyData[]>([]);
  const [companyId, setCompanyId] = useState(0);
  const [selectCompany, setSelectCompany] = useState<CompanyData | null>({});

  const [currentPage, setCurrentPage] = useState(1);

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
      .get(`${process.env.NEXT_PUBLIC_URL}/companies?page=${currentPage}&size=200`, {
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

  const today = format(new Date(), "yyyy.MM.dd");

  const [selectedcompanyMemberId, setSelectedcompanyMemberId] = useState<
    number | null
  >(null);
  const [showModal, setShowModal] = useState<boolean>(false);

  const [staffList, list] = StaffAxios(
    `${process.env.NEXT_PUBLIC_URL}/companymembers?page=${currentPage}&status=&companyId=${companyId}`,
    currentPage
  );

  const openModalClick = (id: number) => {
    setSelectedcompanyMemberId(id);
    setShowModal(true);
  };

  const closeModal = () => {
    setShowModal(false);
  };

  const setPage = (pageNumber: number) => {
    setCurrentPage(pageNumber);
  };

  return (
    <>
      <Navi />
      <div className="flex flex-col w-full">
        <div className="flex"></div>
        <div className="flex justify-end pt-5 pb-3 pr-10">
          <SelectCompany
            companies={companies}
            mycompanies={mycompanies}
            setCompanyId={setCompanyId}
          />
        </div>
        <div className="h-screen w-full flex justify-center">
          <Bigsquare>
            <div className="bg-white p-3 m-5 flex justify-between">
              <div className="flex">
                <div>나의 직원들</div>
              </div>
              <div>{today}</div>
            </div>

            <ListBox>
              <div>사번</div>
              <div>이름</div>
              <div className="pl-10">부서</div>
              <div className="pl-5 pr-4">직급</div>
              <div className="pr-10"></div>
            </ListBox>
            {staffList && staffList.data && (
              <>
                {staffList.data.map((item: Staff) => (
                  <ListBox key={item.companyMemberId}>
                    <div>{item.companyMemberId}</div>
                    <div>{item.name}</div>
                    <div>{item.team}</div>
                    <div>{item.grade}</div>

                    <button
                      className="text-sm font-bold hover:bg-gray-300"
                      onClick={() => openModalClick(item.companyMemberId)}
                    >
                      <EditOutlinedIcon />
                    </button>

                    {selectedcompanyMemberId === item.companyMemberId &&
                      showModal && (
                        <MyStaffModal
                          onClose={closeModal}
                          companyId={item.companyId}
                          companymemberId={item.companyMemberId}
                        ></MyStaffModal>
                      )}
                  </ListBox>
                ))}
              </>
            )}
   
          
          </Bigsquare>
       
        </div>
        <div className="flex justify-center mt-2 mb-2">
              <Pagination
                currentPage={currentPage}
                count={list}
                setPage={setPage}
              />
        </div>
      </div>
    </>
  );
}
