import React from "react";
import UserState from "./User/UserState";
import ProfileState from "./Profile/ProfileState";

function CombinedContextProvider({ children }) {
  return (
    <UserState>
      <ProfileState>{children}</ProfileState>
    </UserState>
  );
}

export default CombinedContextProvider;
