import React from "react";

interface PaginationProps {
  currentPage: number;
  count: number;
  setPage: (pageNumber: number) => void;
}

export default function Pagination({
  currentPage,
  count,
  setPage,
}: PaginationProps) {
  const totalPages = Math.ceil(count / 10); // 한 페이지에 표시되는 아이템 수로 나눈 총 페이지 수 계산

  const handlePrevPage = () => {
    if (currentPage > 1) {
      setPage(currentPage - 1);
    }
  };

  const handleNextPage = () => {
    if (currentPage < totalPages) {
      setPage(currentPage + 1);
    }
  };

  return (
    <>
      <button
        className="px-2 py-1 border border-gray-300 rounded-md cursor-pointer bg-gray-100 mr-2 hover:bg-gray-200"
        onClick={handlePrevPage}
        disabled={currentPage === 1}
      >
        이전
      </button>
      <div className="text-black mr-2">{currentPage}</div>
      <button
        className="px-2 py-1 border border-gray-300 rounded-md cursor-pointer bg-gray-100 hover:bg-gray-200"
        onClick={handleNextPage}
        disabled={currentPage === totalPages}
      >
        다음
      </button>
    </>
  );
}
