"use client";

import { data } from "autoprefixer";
import axios from "axios";
import { useState } from "react";
import SignupFrom from "../components/SignupForm";

export default function singup() {
  return (
    <main className="flex justify-center">
      <SignupFrom></SignupFrom>
    </main>
  );
}
