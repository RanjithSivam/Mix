import axios from "axios";
import { UPDATE } from "../../Util/loginStatus";
import { ERROR, FETCH_ALL_PROFILE } from "../../Util/profileEnum";

const instance = axios.create({
  baseURL: "/api/users",
  headers: {
    Accept: "application/json",
  },
});

export const fetchAllProfile = async (jwt, dispatch) => {
  try {
    const { data } = await instance.get("/", {
      headers: {
        Authorization: jwt,
      },
    });

    if (data.error) throw new Error(data.message);

    dispatch({
      type: FETCH_ALL_PROFILE,
      payload: {
        profiles: data.payload,
      },
    });
  } catch (err) {
    dispatch({
      type: ERROR,
      payload: {
        message: err.message,
      },
    });
  }
};

export const followProfile = async (jwt, dispatch, input) => {
  try {
    const { data } = await instance.post("/follow", input, {
      headers: {
        Authorization: jwt,
      },
    });

    if (data.error) throw new Error(data.message);

    dispatch({
      type: UPDATE,
      payload: {
        user: data.payload,
      },
    });
  } catch (err) {
    dispatch({
      type: ERROR,
      payload: {
        error: true,
        message: err.message,
      },
    });
  }
};

export const unFollowProfile = async (jwt, dispatch, input) => {
  try {
    const { data } = await instance.post("/unfollow", input, {
      headers: {
        Authorization: jwt,
      },
    });

    if (data.error) throw new Error(data.message);

    dispatch({
      type: UPDATE,
      payload: {
        user: data.payload,
      },
    });
  } catch (err) {
    dispatch({
      type: ERROR,
      payload: {
        error: true,
        message: err.message,
      },
    });
  }
};
