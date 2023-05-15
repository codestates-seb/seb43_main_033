// import axios from "axios";

interface SalaryData {
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
}

// async function getData() {
//   let res = "";
//   await axios
//     .get("https://c49c-61-254-8-200.ngrok-free.app/salarystatements/1")
//     .then((response) => {
//       console.log(response);
//       res = response.data;
//     });

//   return res;
// }

// export default async function paystub() {
//   const data = await getData();
//   return (
//     <main>
//       <Paystubs data={dummydata}></Paystubs>
//     </main>
//   );
// }

export default function PaystubPreview() {
  const data: SalaryData = {
    companyName: "난쟁컴퍼니",
    memberName: "난쟁이",
    year: 2023,
    month: 5,
    basePay: 3000,
    overtimePay: 30000,
    nightWorkAllowance: 25000,
    holidayWorkAllowance: 560000,
    salary: 4000000,
    incomeTax: 400000,
    nationalCoalition: 20000,
    healthInsurance: 1000,
    employmentInsurance: 3000,
  };
  return (
    <div className="w-[600px] pb-20 bg-white flex justify-start flex-col rounded-3xl shadow-xl">
      <div className="mt-10 mx-10 text-slate-500 font-semibold">
        {data.companyName}
      </div>
      <div className="mb-10 p-3 h-12 w-full justify-center flex ">
        <h1>{`${data.memberName}님의 ${data.year}년 ${data.month}월 급여`}</h1>
      </div>
      <div className="flex flex-row justify-around">
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
            <div>{Math.floor(data.salary / 10) * 10}</div>
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
                Math.floor(data.employmentInsurance / 10) * 10 +
                Math.floor(data.employmentInsurance / 10) * 10}
            </div>
          </div>
        </div>
      </div>
      <div className="flex justify-end">
        <button className="px-3 py-2 m-16 mb-0 rounded-lg w-fit text-white bg-green-300 hover:bg-green-200">
          email
        </button>
      </div>
    </div>
  );
}
