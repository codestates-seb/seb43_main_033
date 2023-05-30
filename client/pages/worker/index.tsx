import Navi from "../../components/WorkerNavi";
import TopInformation from "../../components/WorkerHome/TopInformation";
import { useRouter } from "next/router";
import { useEffect } from "react";

export default function ManagerHome() {
  const router = useRouter();
  useEffect(() => {
    const searchParamsToken = new URLSearchParams(window.location.search);
    const token = searchParamsToken.get("Authorization");

    const searchParamsId = new URLSearchParams(window.location.search);
    const memberid = searchParamsId.get("MemberId");
    if (token && memberid) {
      localStorage.setItem("token", token);
      localStorage.setItem("memberid", memberid);
      router.push("/worker");
      window.location.href = "/worker";
    }
  }, []);

  return (
    <>
      <Navi />
      <section className="flex-1 flex flex-col px-8 py-4 bg-stone-50">
        <TopInformation />
      </section>
    </>
  );
}
