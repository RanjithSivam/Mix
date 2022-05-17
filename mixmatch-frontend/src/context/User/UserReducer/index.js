import {
  LOADING,
  LOGIN,
  LOGOUT,
  REGISTER,
  ERROR,
  UPDATE,
} from "../../../Util/loginStatus";

export default (state, action) => {
  switch (action.type) {
    case LOADING:
      return {
        ...state,
        loading: action.payload.loading,
      };
    case ERROR:
      return {
        ...state,
        error: action.payload.error,
        message: action.payload.message,
      };
    case LOGIN:
      return {
        ...state,
        user: action.payload.user,
        jwt: action.payload.jwt,
        message: action.payload.message,
        error: false,
      };
    case UPDATE:
      return { ...state, user: action.payload.user };
    case LOGOUT:
      return {
        ...state,
        user: {},
        jwt: "",
        message: action.payload.message,
        error: false,
      };
    case REGISTER:
      return { ...state, message: action.payload.message, error: false };
    default:
      return state;
  }
};
