import React from "react";

type ModalProps = {
  children: React.ReactNode;
  onClose: () => void;
};


export default function StaffSelectModal({ children, onClose }: ModalProps) {
  if (!Array.isArray(children)) {
    return null;
  }

  return (
    <div className="fixed z-10 inset-0 overflow-y-auto">
        <div className="flex items-center justify-center min-h-screen px-4">
        <div className="fixed inset-0 transition-opacity" aria-hidden="true">
            <div className="absolute inset-0 bg-gray-500 opacity-75"></div>
        </div>
        <div className="bg-white rounded-lg z-10 w-full max-w-lg p-6 modal-content">
            {children.map((child, index) => {
            if (index % 2 === 0) {
                return (
                <div className="flex space-x-4 my-3" key={`grid-row-${index}`}>
                  {children[index]}
                  {children[index + 1]}
                </div>
                );
            } else {
                return null;
            }
            })}
          <div className="modal-close flex justify-end">
          <button onClick={onClose}>Yes</button>
          <button className="ml-5" onClick={onClose}>No</button>
          </div>
        </div>
      </div>
    </div>
  );
}
