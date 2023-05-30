export default function LogoutBtn() {
  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("refresh");
    localStorage.removeItem("memberid");
    window.location.href = "/";
  };
  return (
    <>
      <div className="px-5" onClick={handleLogout}>
        로그아웃
      </div>
    </>
  );
}
