import axios from "axios";

export default function UserDelete({
  setDeleteModal,
}: {
  setDeleteModal: any;
}) {
  const deleteHandler = () => {
    const memberid = localStorage.getItem("memberid");
    const token = localStorage.getItem("token");
    axios
      .delete(`${process.env.NEXT_PUBLIC_URL}/members/${memberid}`, {
        headers: {
          Authorization: token,
        },
      })
      .then((res) => {
        setDeleteModal(false);
        localStorage.removeItem("token");
        localStorage.removeItem("refresh");
        localStorage.removeItem("memberid");
        window.location.href = "/";
      })
      .catch((err) => console.log(err));
  };
  return (
    <div className="fixed pt-40 z-10 inset-0 overflow-y-auto">
      <div className="flex items-center justify-center min-h-screen px-4">
        <div className="fixed inset-0 transition-opacity" aria-hidden="true">
          <div className="absolute inset-0 bg-gray-500 opacity-75"></div>
        </div>
        <div className="bg-white rounded-md z-10 w-full max-w-[600px] p-10 modal-content">
          <div className="flex justify-end">
            <button
              className="ml-5 fond-bold mb-3"
              onClick={() => {
                setDeleteModal(false);
              }}
            >
              X
            </button>
          </div>
          <div>
            <p className="text-[20px] mb-5">탈퇴하시겠습니까?</p>
            <button
              className="text-gray-500 hover:text-gray-300 mr-5"
              onClick={deleteHandler}
            >
              네
            </button>
            <button
              className="text-gray-500 hover:text-gray-300"
              onClick={() => setDeleteModal(false)}
            >
              아니오
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
