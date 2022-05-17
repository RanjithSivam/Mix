import { useContext, useReducer } from "react";
import { ProfileContext } from "./ProfileContext";
import { reducer as ProfileReducer } from "./ProfileReducer";

export const useProfile = () => {
  const { state, dispatch } = useContext(ProfileContext);
  return [state, dispatch];
};

const ProfileState = ({ children }) => {
  const initialState = {
    profiles: [],
    message: "",
  };

  const [state, dispatch] = useReducer(ProfileReducer, initialState);

  return (
    <ProfileContext.Provider value={{ state, dispatch }}>
      {children}
    </ProfileContext.Provider>
  );
};

export default ProfileState;
