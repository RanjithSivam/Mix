import axios from "axios";
import { ERROR, LOADING, LOGIN, REGISTER } from "../../../Util/loginStatus";

const instance = axios.create({
  baseURL: "/api/users",
  headers: {
    Accept: "application/json",
  },
});

export const setLoading = (dispatch, status) =>
  dispatch({ type: LOADING, payload: { loading: status } });

export const setError = (dispatch, error) =>
  dispatch({
    type: ERROR,
    payload: { error: error.status, message: error.message },
  });

export const logUser = async (dispatch, input) => {
  setLoading(dispatch, true);
  try {
    const { data } = await instance.post("/login", input);
    dispatch({
      type: LOGIN,
      payload: {
        user: data.payload.userProfile,
        jwt: data.payload.jwt,
        message: data.message,
      },
    });
  } catch (err) {
    dispatch({
      type: ERROR,
      payload: {
        error: true,
        message: err,
      },
    });
  }
  setLoading(dispatch, false);
};

export const registerUser = async (dispatch, input) => {
  setLoading(dispatch, true);
  try {
    const { data } = await instance.post("/signup", input);

    if (data.error) {
      throw new Error(data.message);
    }

    dispatch({
      type: REGISTER,
      payload: {
        message: data.message,
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
  setLoading(dispatch, false);
};

export const updateUser = async (dispatch, jwt, input) => {
  setLoading(dispatch, true);

  try {
    const { data } = await instance.put("/update", input, {
      headers: {
        Authorization: jwt,
      },
    });

    console.log(data);
  } catch (err) {}

  setLoading(dispatch, false);
};
