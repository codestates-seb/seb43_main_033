import axios from "axios";
import { useEffect, useState } from "react";

export default function CompanySearch({ setShowModal }: { setShowModal: any }) {
  const [companydata, setCompanyData] = useState([]);
  const [search, setSearch] = useState("");
  const [selectedCompanyId, setSelectedCompanyId] = useState(0);
  const [companyName, setCompanyName] = useState("");
  const [grade, setGrade] = useState("");
  const [team, setTeam] = useState("");

  useEffect(() => {
    const fetchData = async () => {
      try {
        const token = localStorage.getItem("token");
        const response = await axios.get(
          `${process.env.NEXT_PUBLIC_URL}/companies?page=1&size=200`,
          {
            headers: {
              Authorization: token,
            },
          }
        );
        console.log(response.data.data);
        setCompanyData(response.data.data);
      } catch (error) {
        // 에러 처리
      }
    };
    fetchData();
  }, []);
  const handleSubmit = () => {
    const token = localStorage.getItem("token");
    const memberId = localStorage.getItem("memberid");
    const data = {
      memberId,
      companyId: selectedCompanyId,
      grade,
      team,
    };
    axios
      .post(`${process.env.NEXT_PUBLIC_URL}/companymembers`, data, {
        headers: {
          Authorization: token,
        },
      })
      .then((res) => {
        console.log(res);
        setShowModal(false);
      })
      .catch((err) => {
        console.log(err);
      });
  };
  const handleClick = (companyid: any, companyName: any) => {
    setSelectedCompanyId(companyid);
    setSearch("");
    setCompanyName(companyName);
  };
  const filteredData = companydata.filter((el: any) =>
    el.companyName.includes(search)
  );
  return (
    <div>
      <div className="ml-10">
        <div className="fixed pt-40 z-10 inset-0 overflow-y-auto">
          <div className="flex items-center justify-center min-h-screen px-4">
            <div
              className="fixed inset-0 transition-opacity"
              aria-hidden="true"
            >
              <div className="absolute inset-0 bg-gray-500 opacity-75"></div>
            </div>
            <div className="bg-white rounded-md z-10 w-full max-w-[500px] p-10 modal-content">
              <div className="flex justify-end">
                <button
                  className="ml-5 fond-bold mb-3"
                  onClick={() => setShowModal(false)}
                >
                  X
                </button>
              </div>
              <input
                className="ml-2 px-2 py-1 border border-blue-300  outline-offset-2 outline-blue-300"
                value={search}
                onChange={(e) => setSearch(e.target.value)}
                placeholder="🔎 search..."
              ></input>
              <div className="fixed bg-white w-[400px] h-min-[200px] ">
                {filteredData && search
                  ? filteredData.map((el: any, idx) => {
                      return (
                        <div
                          key={idx}
                          onClick={() =>
                            handleClick(el.companyId, el.companyName)
                          }
                          className="flex hover:bg-gray-100"
                        >
                          {el.companyName}
                        </div>
                      );
                    })
                  : null}
              </div>
              <div className="flex">
                <div className="m-3 bg-gray-200 px-2 w-fit">회사이름</div>
                {selectedCompanyId ? (
                  <div className="m-3">{companyName}</div>
                ) : null}
              </div>
              <div className="flex">
                <label htmlFor="grade" className="m-3 bg-gray-200 px-2 w-fit">
                  사원직급
                </label>
                <input
                  className="p-0 px-2 h-9 outline-none border-b-2"
                  id="grade"
                  value={grade}
                  onChange={(e) => setGrade(e.target.value)}
                ></input>
              </div>
              <div className="flex">
                <label htmlFor="team" className="m-3 bg-gray-200 px-2 w-fit">
                  사원소속부서
                </label>
                <input
                  className="p-0 px-2 h-9 outline-none border-b-2"
                  id="team"
                  value={team}
                  onChange={(e) => setTeam(e.target.value)}
                ></input>
              </div>
              <button
                onClick={handleSubmit}
                className="bg-green-300 py-1 px-2 rounded-md text-white text-[13px] hover:bg-green-500 mt-5"
              >
                승인요청
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
