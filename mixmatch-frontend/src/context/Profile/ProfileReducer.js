import {
  ERROR,
  FETCH_ALL_PROFILE,
  FOLLOW,
  UNFOLLOW,
} from "../../Util/profileEnum";

export const reducer = (state, action) => {
  switch (action.type) {
    case FETCH_ALL_PROFILE:
      return { ...state, profiles: action.payload.profiles, message: "" };
    case FOLLOW:
      return { ...state };
    case UNFOLLOW:
      return { ...state };
    case ERROR:
      return { ...state, message: action.payload.message };
    default:
      break;
  }
};
