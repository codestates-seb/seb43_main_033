import Navi from "../../components/WorkerNavi";
import TopInformation from "../../components/WorkerHome/TopInformation";
import UserInfoEdit from "../../components/WorkerHome/UserInfoEdit";
import UserInfo from "../../components/WorkerHome/UserInfo";

export default function ManagerHome() {
  return (
    <>
      <Navi />
      <section className="flex-1 flex flex-col px-8 py-4 bg-stone-50">
        <main className="flex flex-col flex-wrap min-h-32 bg-white p-6 mb-5 h-screen rounded drop-shadow">
          <TopInformation />
          <UserInfo />
        </main>
      </section>
      <section></section>
    </>
  );
}
