import React from "react";

const TopBar = ({ user }: any) => {
  return (
    <header
      className="grid grid-cols-6 items-center px-6 py-3 shadow h-full sm:grid-cols-6  gap-2">
      <h1 className="col-span-1 text-xl font-semibold text-center sm:text-left" >
        Dashboard
      </h1>
    </header>
  );
};

export default TopBar;
