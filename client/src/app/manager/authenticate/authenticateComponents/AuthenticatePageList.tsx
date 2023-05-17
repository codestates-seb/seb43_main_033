import AuthenticatePageListLi from "../../../../../components/authenticateComponents/AuthenticatePageListLi";

interface AuthenticatePageListProps {
  prop: {
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
  };
  handleOnDelete: (b_no: string) => void;
}

export default function AuthenticatePageList({
  prop,
  handleOnDelete,
}: AuthenticatePageListProps) {
  const informationList = [
    {
      label: "사업자 대표명:",
      description: prop.p_nm,
    },
    {
      label: "사업자 등록번호:",
      description: prop.b_no,
    },
    {
      label: "납세자 상태(명칭):",
      description: prop.b_stt,
    },
    {
      label: "과세 유형:",
      description: prop.tax_type,
    },
  ];

  return (
    <li className="flex flex-wrap min-h-32">
      <label className="flex flex-wrap">
        <svg
          className="h-32 w-32"
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 96 960 960"
        >
          <path d="M160 856v-60h386v60H160Zm0-166v-60h640v60H160Zm0-167v-60h640v60H160Zm0-167v-60h640v60H160Z" />
        </svg>
      </label>
      <div className="flex-1 flex flex-wrap flex-col p-3 bg-white rounded drop-shadow-md">
        {informationList.map((x) => {
          return (
            <AuthenticatePageListLi
              label={x.label}
              description={x.description}
            />
          );
        })}
      </div>
      <span className="flex items-center">
        <button onClick={() => handleOnDelete(prop.b_no)}>
          <svg
            xmlns="http://www.w3.org/2000/svg"
            className="h-12 w-12"
            viewBox="0 96 960 960"
          >
            <path d="m448 730 112-112 112 112 43-43-113-111 111-111-43-43-110 112-112-112-43 43 113 111-113 111 43 43ZM120 576l169-239q13-18 31-29.5t40-11.5h420q24.75 0 42.375 17.625T840 356v440q0 24.75-17.625 42.375T780 856H360q-22 0-40-11.5T289 815L120 576Zm75 0 154 220h431V356H349L195 576Zm585 0V356v440-220Z" />
          </svg>
        </button>
      </span>
    </li>
  );
}
