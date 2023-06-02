import { useEffect, useState } from "react";
import TopInformationLi from "./TopInfo/TopInformationLi";
import axios from "axios";
import UserInfoEdit from "./UserInfoEdit";
import UserDelete from "./UserDelete";

export default function UserInfo() {
  const [informationList, setInformationList] = useState([]);
  const [edit, setEdit] = useState(false);
  const [info, setInfo] = useState([]);
  const [deleteModal, setDeleteModal] = useState(false);
  useEffect(() => {
    const memberid = localStorage.getItem("memberid");
    const token = localStorage.getItem("token");
    axios
      .get(`${process.env.NEXT_PUBLIC_URL}/members/${memberid}`, {
        headers: {
          Authorization: token,
        },
      })
      .then((res) => {
        console.log(res);
        const resData = res.data;
        const info: any = [
          { label: "이름", description: res.data.name },
          { label: "전화번호", description: res.data.phoneNumber },
          { label: "메일", description: res.data.email },
          { label: "생년월일", description: res.data.birthday },
          { label: "주소", description: res.data.address },
          { label: "직급", description: res.data.grade },
        ];
        setInfo(resData);
        setInformationList(info);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  const editClickHandler = () => {
    setEdit(true);
  };

  return (
    <div className=" mt-5">
      <section className="flex flex-col p-4 bg-stone-100 rounded">
        <h1 className="bg-white text-[18px] font-semibold text-gray-700  rounded-sm px-2 mb-2">
          회원정보
        </h1>
        <div className="flex justify-start">
          <div className="px-2">
            {informationList.length &&
              informationList.map((x: any, idx) => {
                return (
                  <div key={idx}>
                    <TopInformationLi
                      label={x.label}
                      description={x.description}
                    />
                  </div>
                );
              })}
          </div>
        </div>
        <div className="flex justify-end mx-10">
          <button className="text-gray-400 mx-10" onClick={editClickHandler}>
            수정
          </button>
          {edit && <UserInfoEdit setEdit={setEdit} info={info} />}
          <button
            className="text-gray-400 "
            onClick={() => setDeleteModal(true)}
          >
            회원탈퇴
          </button>
          {deleteModal && <UserDelete setDeleteModal={setDeleteModal} />}
        </div>
      </section>
    </div>
  );
}
