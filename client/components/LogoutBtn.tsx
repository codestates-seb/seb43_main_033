import { useRouter } from "next/router";

export default function LogoutBtn() {
  const router = useRouter();
  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("refresh");
    router.push("/");
    router.reload();
  };
  return (
    <>
      <div className="px-5" onClick={handleLogout}>
        로그아웃
      </div>
    </>
  );
}
