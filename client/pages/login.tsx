import LoginForm from "../components/LoginForm";

export default function login() {
  return (
    <main
      key="login"
      className="w-screen flex items-center justify-center bg-gray-50 p-48 flex-col"
    >
      <div className="flex flex-col justify-center p-12 rounded-lg shadow-lg bg-white w-[366px]">
        <div className="flex flex-row justify-center mb-10">
          <h1 className="font-semibold text-green-500">WORKER LOGIN</h1>
        </div>
        <LoginForm></LoginForm>
      </div>
      <div className="flex mt-3">
        <p>계정이 없으신가요?</p>
        <a className="ml-3 text-blue-400" href="/signup">
          회원가입
        </a>
      </div>
    </main>
  );
}
