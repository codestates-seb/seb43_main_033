import axios from "axios";
import { useRouter } from "next/router";
import { useEffect, useState } from "react";

export default function PaystubPreview({
  companyId,
  selectedCompanyMemberId,
  isMyPaystub,
}: {
  companyId?: number;
  selectedCompanyMemberId?: number;
  isMyPaystub?: boolean;
}) {
  const [data, setData] = useState<SalaryData | null>(null);
  const [name, setName] = useState(null);
  const [mypaystublist, setMypaystublist] = useState<any>([]);
  const [paystubId, setPaystubId] = useState(0);
  const [managerPaysutbId, setManagerPaystubId] = useState(0);
  const [paystubExist, setPaystubExist] = useState(false);
  const currentDate = new Date();
  const month = currentDate.getMonth() + 1;
  const year = currentDate.getFullYear();
  const router = useRouter();

  {
    selectedCompanyMemberId &&
      useEffect(() => {
        const token = localStorage.getItem("token");
        axios
          .get(
            `${process.env.NEXT_PUBLIC_URL}/manager/${companyId}/members/${selectedCompanyMemberId}/paystub?year=${year}&month=${month}`,
            {
              headers: {
                Authorization: token,
              },
            }
          )
          .then((response) => {
            setData(response.data.statement);
            setName(response.data.member.name);
            setPaystubExist(response.data.exist);
            setManagerPaystubId(response.data.salaryStatementId);
          })
          .catch((err) => {
            console.log(err);
            setData(null);
          });
      }, [selectedCompanyMemberId]);
  }

  const handleMyPaystub = () => {
    const token = localStorage.getItem("token");
    axios
      .get(`${process.env.NEXT_PUBLIC_URL}/worker/mypaystub`, {
        headers: {
          Authorization: token,
        },
      })
      .then((res) => {
        setMypaystublist(res.data);
      })
      .catch(() => {});
  };
  useEffect(() => {
    isMyPaystub ? handleMyPaystub() : null;
  }, []);

  const handlePaystub = () => {
    const token = localStorage.getItem("token");
    axios
      .post(
        `${process.env.NEXT_PUBLIC_URL}/manager/${companyId}/members/${selectedCompanyMemberId}/paystub?year=${year}&month=${month}`,
        null,
        {
          headers: {
            Authorization: token,
          },
        }
      )
      .then((response) => {
        router.reload();
      })
      .catch((err) => console.log(err));
  };
  const handleSelect = (e: any) => {
    setPaystubId(e.target.value);
  };
  useEffect(() => {
    const token = localStorage.getItem("token");
    {
      paystubId && isMyPaystub
        ? axios
            .get(`${process.env.NEXT_PUBLIC_URL}/paystub/${paystubId}`, {
              headers: {
                Authorization: token,
              },
            })
            .then((res) => {
              setData(res.data);
              setName(res.data.name);
            })
        : null;
    }
  }, [paystubId]);
  const handlePDFdownload = () => {
    const token = localStorage.getItem("token");
    axios
      .get(`${process.env.NEXT_PUBLIC_URL}/paystub/${paystubId}/file`, {
        responseType: "arraybuffer",
        headers: {
          Authorization: token,
          Accept: "application/pdf",
        },
      })
      .then((res) => {
        const blob = new Blob([res.data], {
          type: "application/pdf;charset=UTF-8'",
        });
        const fileUrl = window.URL.createObjectURL(blob);
        const link = document.createElement("a");
        link.href = fileUrl;
        link.style.display = "none";
        const injectFilename = (res: any) => {
          const disposition = res.headers["content-disposition"];
          const fileName = decodeURI(
            disposition
              .match(/filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/)[1]
              .replace(/['"]/g, "")
          );
          return fileName;
        };
        link.download = injectFilename(res);
        document.body.appendChild(link);
        link.click();
        link.remove();
      })
      .catch((err) => err);
  };
  const handleEmail = () => {
    const token = localStorage.getItem("token");
    axios
      .post(
        `${process.env.NEXT_PUBLIC_URL}/paystub/${managerPaysutbId}/email`,
        null,
        {
          headers: {
            Authorization: token,
          },
        }
      )
      .then(() => {})
      .catch(() => {});
  };

  const handlPaystubDelete = () => {
    const token = localStorage.getItem("token");
    axios
      .delete(`${process.env.NEXT_PUBLIC_URL}/paystub/${managerPaysutbId}`, {
        headers: {
          Authorization: token,
        },
      })
      .then(() => {
        router.reload();
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return (
    <div>
      {mypaystublist.length ? (
        <div>
          <select onClick={(e) => handleSelect(e)}>
            {mypaystublist.map((el: any, idx: number) => {
              return (
                <option key={idx} value={el.id}>
                  {`${el.year}년 ${el.month}월 급여명세서`}
                </option>
              );
            })}
          </select>
        </div>
      ) : null}
      {data ? (
        <div className="w-[600px] ml-0 bg-white flex justify-center flex-col rounded-xl shadow-xl p-10">
          <div className=" p-3 h-12 w-full justify-center flex  items-end">
            <h1>{`${name}님의 ${data.year}년 ${data.month}월 급여`}</h1>
          </div>
          {paystubExist && (
            <div className="flex justify-end px-10">
              <p className=" ml-5 text-[10px]  text-red-500">발급완료</p>
              <button
                onClick={handlPaystubDelete}
                className="ml-5 text-[10px]  text-gray-500"
              >
                삭제하기
              </button>
            </div>
          )}
          <div className="flex flex-row justify-around  mt-10">
            <div>
              <div className="flex flex-row p-3  justify-between w-[200px]">
                <div>기본급</div>
                <div>{data.basePay}</div>
              </div>
              <div className="flex flex-row p-3  justify-between">
                <div>연장근로수당</div>
                <div>{Math.floor(data.overtimePay / 10) * 10}</div>
              </div>
              <div className="flex flex-row p-3 justify-between">
                <div>야간근로수당</div>
                <div>{Math.floor(data.nightWorkAllowance / 10) * 10}</div>
              </div>
              <div className="flex flex-row p-3 justify-between">
                <div>휴일근로수당</div>
                <div>{Math.floor(data.holidayWorkAllowance / 10) * 10}</div>
              </div>
              <div className="flex flex-row p-3 justify-between mt-2 pt-2 border-t-2 border-dashed">
                <div>총 지급금액</div>
                <div>
                  {Math.floor(data.basePay / 10) * 10 +
                    Math.floor(data.overtimePay / 10) * 10 +
                    Math.floor(data.nightWorkAllowance / 10) * 10 +
                    Math.floor(data.holidayWorkAllowance / 10) * 10}
                </div>
              </div>
            </div>
            <div>
              <div className="flex flex-row p-3 justify-between w-[200px]">
                <div>소득세</div>
                <div>{Math.floor(data.incomeTax / 10) * 10}</div>
              </div>
              <div className="flex flex-row p-3 justify-between">
                <div>국민연금</div>
                <div>{Math.floor(data.nationalCoalition / 10) * 10}</div>
              </div>
              <div className="flex flex-row p-3 justify-between">
                <div>건강보험</div>
                <div>{Math.floor(data.healthInsurance / 10) * 10}</div>
              </div>
              <div className="flex flex-row p-3 justify-between">
                <div>고용보험</div>
                <div>{Math.floor(data.employmentInsurance / 10) * 10}</div>
              </div>
              <div className="flex flex-row p-3 justify-between mt-2 pt-2 border-t-2 border-dashed">
                <div>총 공제 금액</div>
                <div>
                  {Math.floor(data.incomeTax / 10) * 10 +
                    Math.floor(data.nationalCoalition / 10) * 10 +
                    Math.floor(data.healthInsurance / 10) * 10 +
                    Math.floor(data.employmentInsurance / 10) * 10}
                </div>
              </div>
              <div className="flex flex-row p-3 justify-between mt-2 pt-2 border-t-2 border-dashed"></div>
              <div className="flex flex-row p-3 justify-between mt-2 pt-2 border-t-2 border-dashed">
                <div>실수령액</div>
                <div>
                  {Math.floor(data.basePay / 10) * 10 +
                    Math.floor(data.overtimePay / 10) * 10 +
                    Math.floor(data.nightWorkAllowance / 10) * 10 +
                    Math.floor(data.holidayWorkAllowance / 10) * 10 -
                    (Math.floor(data.incomeTax / 10) * 10 +
                      Math.floor(data.nationalCoalition / 10) * 10 +
                      Math.floor(data.healthInsurance / 10) * 10 +
                      Math.floor(data.employmentInsurance / 10) * 10)}
                </div>
              </div>
            </div>
          </div>
          <div className="flex justify-end">
            {isMyPaystub ? null : (
              <button
                onClick={handleEmail}
                className="px-3 py-2 m-16 mb-0 rounded-lg w-fit text-white bg-green-300 hover:bg-green-500"
              >
                email
              </button>
            )}
            {isMyPaystub ? (
              <button
                className="px-3 py-2 m-16 mb-0 rounded-lg w-fit text-white bg-green-300 hover:bg-green-500 "
                onClick={handlePDFdownload}
              >
                pdf download
              </button>
            ) : null}
            {paystubExist || isMyPaystub ? null : (
              <button
                onClick={handlePaystub}
                className="px-3 py-2 m-16 mb-0 rounded-lg w-fit text-white bg-green-300 hover:bg-green-500"
              >
                발급
              </button>
            )}
          </div>
        </div>
      ) : null}
    </div>
  );
}

export interface SalaryData {
  companyName: string;
  memberName: string;
  year: number;
  month: number;
  basePay: number;
  overtimePay: number;
  nightWorkAllowance: number;
  holidayWorkAllowance: number;
  salary: number;
  incomeTax: number;
  nationalCoalition: number;
  healthInsurance: number;
  employmentInsurance: number;
  totalSalary: number;
}
