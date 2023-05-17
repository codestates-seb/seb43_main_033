import Bigsquare from "../../components/Bigsquare";
import PaystubPreview from "../../components/PaystubPage/PaystubPreview";
import Navi from "../../components/WorkerNavi";

export default function Mypaystub(): JSX.Element {
  const isMyPaystub: boolean = true;
  return (
    <div className="flex w-full">
      <Navi />
      <div className="w-full flex justify-center">
        <Bigsquare>
          <div className="bg-white p-3 m-5 flex">
            <div>나의 급여명세서</div>
            <div>5월</div>
          </div>
          <div className="flex m-5">
            <PaystubPreview isMyPaystub={isMyPaystub} />
          </div>
        </Bigsquare>
      </div>
    </div>
  );
}
