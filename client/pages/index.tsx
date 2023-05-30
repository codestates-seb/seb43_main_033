import MainContents from '../components/Main/MainContents';
import { Inter } from "next/font/google";
import { useRouter } from "next/router";
import { useEffect, useState } from "react";

const inter = Inter({ subsets: ["latin"] });

export default function Home() {
  const router = useRouter();
  useEffect(() => {
    const searchParamsToken = new URLSearchParams(window.location.search);
    const token = searchParamsToken.get("Authorization");
    const searchParamsId = new URLSearchParams(window.location.search);
    const memberid = searchParamsId.get("MemberId");
    if (token && memberid) {
      localStorage.setItem("token", token);
      localStorage.setItem("memberid", memberid);
      router.push("/");
      window.location.href = "/";
    }
  }, []);

  return (        
    <MainContents />
  )
}
