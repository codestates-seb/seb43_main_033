import AuthenticateModalLi from "./AuthenticateModalLi";

interface Status {
  p_nm: string;
  b_no: string;
  b_stt: string;
  tax_type: string;
}

interface Props {
  status: Status;
  isModal: () => void;
}

export default function AuthenticateModal(props: Props) {
  const { status, isModal } = props;
  const informationList = [
    {
      label: "사업자 대표명:",
      description: status.p_nm,
    },
    {
      label: "사업자 등록번호:",
      description: status.b_no,
    },
    {
      label: "납세자 상태(명칭):",
      description: status.b_stt,
    },
    {
      label: "과세유형 메시지:",
      description: status.tax_type,
    },
  ];
  return (
    <article className="flex flex-col flex-wrap p-2 items-center w-1/2 z-10 top-1/2 inset-x-1/2 fixed -translate-y-1/2 -translate-x-1/2 bg-emerald-400 rounded drop-shadow-2xl">
      <button
        onClick={isModal}
        className="flex flex-nowrap justify-center items-center fixed right-0 top-0 w-5 h-5 bg-black rounded-sm"
      ></button>
      <div className="flex flex-1 justify-center items-center font-semibold text-white"></div>
      <section className="flex flex-col p-3 w-full bg-stone-100 rounded">
        <ul>
          {informationList.map((x) => {
            return (
              <AuthenticateModalLi
                label={x.label}
                description={x.description}
              />
            );
          })}
        </ul>
        <div className="flex justify-center items-center">
          <button className="mx-4 my-1 px-2 rounded-sm  bg-emerald-400 hover:bg-emerald-700 hover:text-white">
            등록
          </button>
          <button
            onClick={isModal}
            className="mx-4 my-1 px-2 rounded-sm bg-emerald-400 hover:bg-emerald-700 hover:text-white"
          >
            취소
          </button>
        </div>
      </section>
    </article>
  );
}
