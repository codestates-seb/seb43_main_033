"use client";

import { useState, useEffect } from "react";
import Navi from "../../components/ManagerNavi";
import AuthenticateModal from "../../components/authenticateComponents/AuthenticateModal";
import AuthenticatePageList from "../../components/authenticateComponents/AuthenticatePageList";
import axios from "axios";

interface Inputs {
  b_no: string;
  start_dt: string;
  p_nm: string;
}
interface Status {
  p_nm: string;
  b_no: string;
  b_stt: string;
  b_stt_cd: string;
  end_dt: string;
  invoice_apply_dt: string;
  tax_type: string;
  tax_type_cd: string;
  tax_type_change_dt: string;
  utcc_yn: string;
}

export default function Authenticate() {
  const [status, setStatus] = useState<Status>({
    p_nm: "",
    b_no: "",
    b_stt: "",
    b_stt_cd: "",
    end_dt: "",
    invoice_apply_dt: "",
    tax_type: "",
    tax_type_cd: "",
    tax_type_change_dt: "",
    utcc_yn: "",
  });
  const [inputs, setInputs] = useState<Inputs>({
    b_no: "",
    start_dt: "",
    p_nm: "",
  });
  const [list, setList] = useState<Status[]>([
    {
      p_nm: "예시",
      b_no: "예시",
      b_stt: "예시",
      b_stt_cd: "",
      end_dt: "",
      invoice_apply_dt: "",
      tax_type: "예시",
      tax_type_cd: "",
      tax_type_change_dt: "",
      utcc_yn: "",
    },
  ]);
  const [isAuthenticated, setIsAuthenticated] = useState<boolean | undefined>(
    undefined
  );
  const handleOnInputs = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (isAuthenticated === false || isAuthenticated === true) {
      setIsAuthenticated(undefined);
    }
    const { name, value } = e.target;
    setInputs({ ...inputs, [name]: value });
  };
  const isValid = () => {
    axios
      .post<{
        data: { valid: string; request_param: Inputs; status: Status }[];
      }>(
        `http://api.odcloud.kr/api/nts-businessman/v1/validate?serviceKey=oOB4RY70YOp4tLdsURACan0utiTW2d6VX3xpFRPXQ2MqwJPCZ2dSH%2F9LAa%2B4HXdBt8CZoOepe657IWu66uNVAg%3D%3D&returnType=JSON`,
        {
          businesses: [
            {
              ...inputs,
            },
          ],
        }
      )
      .then((res) => {
        // console.log(res.data.data[0].request_param.p_nm); 입력된 사업자 대표명
        setInputs({
          b_no: "",
          start_dt: "",
          p_nm: "",
        });
        if (res.data.data[0].valid === "02") {
          setIsAuthenticated(false);
        } else if (res.data.data[0].valid === "01") {
          setStatus({            
            ...res.data.data[0].status,
          });
          setIsAuthenticated(true);
        }
      })
      .catch((err) => console.log(err));
  };
  const isModal = () => {
    if (isAuthenticated === false || isAuthenticated === true) {
      setIsAuthenticated(undefined);
    }
  };
  useEffect(() => console.log(status), [status]);

  return (
    <>
      <Navi />
      {isAuthenticated === true && (
        <AuthenticateModal status={status} isModal={isModal} />
      )}
      <section className="flex-1 flex flex-col px-8 py-4 bg-stone-50">
        <article className="flex flex-col flex-wrap min-h-32 bg-white p-6 mb-5 rounded drop-shadow">
          <div className="flex justify-center items-center">
            <span className="mb-3 text-xl font-semibold">
              사업자등록증 정보 인증
            </span>
          </div>
          <section className="flex flex-col p-4 mb-4 bg-stone-100 drop-shadow-lg rounded">
            <div>
              <span className="flex">
                <label className="w-28">사업자등록번호: </label>
                <input
                  type="text"
                  onChange={handleOnInputs}
                  className="flex-1 pl-1 mb-3 border border-black rounded"
                  maxLength={10}
                  value={inputs.b_no}
                  name="b_no"
                />
              </span>
            </div>
            <div>
              <span className="flex">
                <label className="w-28">개업일자: </label>
                <input
                  type="text"
                  onChange={handleOnInputs}
                  placeholder="YYYYMMDD.."
                  className="flex-1 pl-1 mb-3 border border-black rounded"
                  maxLength={8}
                  value={inputs.start_dt}
                  name="start_dt"
                />
              </span>
            </div>
            <div>
              <span className="flex">
                <label className="w-28">대표자 성명: </label>
                <input
                  type="text"
                  onChange={handleOnInputs}
                  className="flex-1 pl-1 mb-3 border border-black rounded"
                  maxLength={20}
                  value={inputs.p_nm}
                  name="p_nm"
                />
              </span>
            </div>
          </section>
          <div className="flex">
            <span
              className={`flex-1 ${
                isAuthenticated === true
                  ? "text-emerald-400"
                  : isAuthenticated === false
                  ? "text-red-500"
                  : null
              }`}
            >
              {isAuthenticated === true
                ? "인증되었습니다."
                : isAuthenticated === false
                ? "확인할 수 없습니다."
                : null}
            </span>
            <button
              onClick={() => {
                inputs.b_no !== "" &&
                inputs.p_nm !== "" &&
                inputs.start_dt !== ""
                  ? isValid()
                  : null;
              }}
              className="px-3 py-1 bg-emerald-400 hover:bg-emerald-800 text-white drop-shadow-lg rounded"
            >
              인증/조회
            </button>
          </div>
        </article>
        <article className="flex flex-col flex-wrap min-h-32 bg-white p-6 mb-5 rounded drop-shadow">
          <div className="flex flex-col justify-center items-center">
            <span className="mb-3 text-xl font-semibold">
              사업자등록증 정보 조회
            </span>
            <ul className="flex flex-col p-3 bg-stone-100 w-full rounded drop-shadow-lg">
              {list.map((x) => {
                return <AuthenticatePageList prop={x} />;
              })}
            </ul>
          </div>
        </article>
      </section>
    </>
  );
}

// b_no	string
// default: 0000000000
// xml: OrderedMap { "name": "BNo" }
// 사업자등록번호

// xml:
//    name: BNo
// b_stt	string
// default: 01
// xml: OrderedMap { "name": "BStt" }
// 납세자상태(명칭):
// 01: 계속사업자,
// 02: 휴업자,
// 03: 폐업자

// xml:
//    name: BStt
// b_stt_cd	string
// default: 01
// xml: OrderedMap { "name": "BSttCd" }
// 납세자상태(코드):
// 01: 계속사업자,
// 02: 휴업자,
// 03: 폐업자

// xml:
//    name: BSttCd
// tax_type	string
// default: 일반과세자
// xml: OrderedMap { "name": "TaxType" }
// 과세유형메세지(명칭):
// 01:부가가치세 일반과세자,
// 02:부가가치세 간이과세자,
// 03:부가가치세 과세특례자,
// 04:부가가치세 면세사업자,
// 05:수익사업을 영위하지 않는 비영리법인이거나 고유번호가 부여된 단체,국가기관 등,
// 06:고유번호가 부여된 단체,
// 07:부가가치세 간이과세자(세금계산서 발급사업자),
// * 등록되지 않았거나 삭제된 경우: "국세청에 등록되지 않은 사업자등록번호입니다"

// xml:
//    name: TaxType
// tax_type_cd	string
// default: 1
// xml: OrderedMap { "name": "TaxType" }
// 과세유형메세지(코드):
// 01:부가가치세 일반과세자,
// 02:부가가치세 간이과세자,
// 03:부가가치세 과세특례자,
// 04:부가가치세 면세사업자,
// 05:수익사업을 영위하지 않는 비영리법인이거나 고유번호가 부여된 단체,국가기관 등,
// 06:고유번호가 부여된 단체,
// 07:부가가치세 간이과세자(세금계산서 발급사업자)

// xml:
//    name: TaxType
// end_dt	string
// default: 20000101
// xml: OrderedMap { "name": "EndDt" }
// 폐업일 (YYYYMMDD 포맷)

// xml:
//    name: EndDt
// utcc_yn	string
// default: Y
// xml: OrderedMap { "name": "UtccYn" }
// 단위과세전환폐업여부(Y,N)

// xml:
//    name: UtccYn
// tax_type_change_dt	string
// default: 20000101
// xml: OrderedMap { "name": "TaxTypeChangeDt" }
// 최근과세유형전환일자 (YYYYMMDD 포맷)

// xml:
//    name: TaxTypeChangeDt
// invoice_apply_dt	string
// default: 20000101
// xml: OrderedMap { "name": "InvoiceApplyDt" }
// 세금계산서적용일자 (YYYYMMDD 포맷)
