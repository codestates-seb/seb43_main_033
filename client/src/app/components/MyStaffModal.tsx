import React from "react";
import Image from "next/image";
import defaultprofile from "../defaultprofile.png";

type ModalProps = {
  children: React.ReactNode;
  onClose: () => void;
};

export default function StaffSelectModal({ onClose }: ModalProps) {
  return (
    <div className="fixed z-10 inset-0 overflow-y-auto">
      <div className="flex items-center justify-center min-h-screen px-4">
        <div className="fixed inset-0 transition-opacity" aria-hidden="true">
          <div className="absolute inset-0 bg-gray-500 opacity-75"></div>
        </div>
        <div className="bg-white rounded-lg z-10 w-full max-w-lg p-6 modal-content">
          <div className="flex justify-between">
            <div>
              <Image
                className="w-40 h-40"
                src={defaultprofile}
                alt="근로계약서"
              />
            </div>
            <div>
              <div className="mt-2 mb-4">사번 : 2023001</div>
              <div className="mb-4">이름 : 홍길동</div>
              <div className="mb-4">부서 : 회계팀</div>
              <div>직급 : 사원</div>
            </div>

            <div>
              <div className="mt-2 mb-4">근무시간: 09:00~18:00</div>
              <div>권리권한: standard</div>
            </div>
          </div>
          <div className="modal-close flex justify-end">
            <button onClick={onClose}>Yes</button>
            <button className="ml-5" onClick={onClose}>
              No
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
