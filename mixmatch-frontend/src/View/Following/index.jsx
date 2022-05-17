import { Grid, Typography, Button, Avatar, Box } from "@mui/material";
import axios from "axios";
import React, { useContext } from "react";
import { useState } from "react";
import { useEffect } from "react";
import { unFollowProfile } from "../../context/Profile/ProfileActions";
import { useUser } from "../../context/User/UserState";

function Following() {
  const [userState, userDispatch] = useUser();

  const [followings, setFollowings] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    getFollowing();
  }, [userState]);

  const getFollowing = async () => {
    setError("");
    try {
      const { data } = await axios.post(
        "/api/users/getfollowing",
        {
          id: userState.user.id,
        },
        {
          headers: {
            Authorization: userState.jwt,
          },
        }
      );

      if (data.error) throw new Error(data.message);

      setFollowings(data.payload);
    } catch (err) {
      setError(err.message);
    }
  };
  return (
    <Box>
      <Typography variant="h4" component="h6" sx={{ textAlign: "center" }}>
        Your Following
      </Typography>
      {error ? (
        <Typography
          variant="p"
          component="p"
          sx={{
            mt: 2,
            fontWeight: "bold",
            textTransform: "uppercase",
            textAlign: "center",
          }}
        >
          {error}
        </Typography>
      ) : (
        <Grid container spacing={2}>
          {followings.map((following) => (
            <Grid
              item
              key={following.id}
              xs={12}
              sx={{ display: "flex", alignItems: "center", gap: 2 }}
            >
              <Avatar
                alt={following.firstName + " " + following.lastName}
                src={following.imageUrl}
              />
              <Typography component="p" variant="h6">
                {following.firstName + " " + following.lastName}
              </Typography>

              <Button
                size="small"
                variant="contained"
                color="success"
                onClick={() =>
                  unFollowProfile(userState.jwt, userDispatch, {
                    follow: following.id,
                    follower: userState.user.id,
                  })
                }
              >
                ✔️ Followed
              </Button>
            </Grid>
          ))}
        </Grid>
      )}
    </Box>
  );
}

export default Following;
