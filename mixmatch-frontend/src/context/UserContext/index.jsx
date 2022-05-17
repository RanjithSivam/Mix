import { createContext, useEffect, useReducer } from "react";
import { LOGIN, LOGOUT, UPDATE_PROFILE } from "../../Util/loginStatus";

export const UserContext = createContext();

const initialState = localStorage.getItem("user")
  ? JSON.parse(localStorage.getItem("user"))
  : {
      user: null,
    };

const reducer = (state, action) => {
  switch (action.type) {
    case LOGIN:
    // case UPDATE_PROFILE:
    //   return { ...state, user: action.payload };
    case LOGOUT:
      return { ...state, user: null };
    default:
      return state;
  }
};

const UserContextProvider = ({ children }) => {
  const [state, dispatch] = useReducer(reducer, initialState);

  useEffect(() => {
    localStorage.setItem("user", JSON.stringify(state));
  }, [state]);

  return (
    <UserContext.Provider value={{ state, dispatch }}>
      {children}
    </UserContext.Provider>
  );
};

export default UserContextProvider;
