import SignupFrom from "../components/SignupForm";

export default function singup() {
  return (
    <main className="flex justify-center">
      <div className="flex justify-center m-0 py-20 w-screen items-center  bg-gray-50">
        <div className="p-5">
          <h1 className="text-[24px] font-semibold mb-10">
            모두의 급여에서 간편하게 월급을 확인하세요
          </h1>
          <ul className="mb-40">
            <li className="mb-5 text-gray-600">
              급여명세서 전송여부를 메일로 알려드립니다
            </li>
            <li className="mb-5 text-gray-600">
              오늘의 근무상황을 체크할 수 있습니다
            </li>
            <li className="mb-5 text-gray-600">
              근로계약서를 확인할 수 있습니다
            </li>
          </ul>
          <div className="flex">
            <p className="text-gray-400">이미 계정이 있으신가요?</p>
            <p className="ml-3 text-blue-300">근로자 로그인</p>
          </div>
        </div>
        <div className="flex flex-col m-0 shadow-lg shadow-slate-200 rounded-lg py-12 px-6 bg-white">
          <div className="flex justify-center mb-10">
            <h1 className="font-semibold text-green-500">WORKER SIGNUP</h1>
          </div>
          <SignupFrom></SignupFrom>
        </div>
      </div>
    </main>
  );
}
