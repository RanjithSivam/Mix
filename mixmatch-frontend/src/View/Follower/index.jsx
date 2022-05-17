import { Avatar, Button, Grid, Typography } from "@mui/material";
import { Box } from "@mui/system";
import axios from "axios";
import { useEffect, useState } from "react";
import { followProfile } from "../../context/Profile/ProfileActions";
import { useUser } from "../../context/User/UserState";

function Follower() {
  const [userState, userDispatch] = useUser();

  const [followers, setFollowers] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    getFollower();
  }, []);

  const getFollower = async () => {
    setError("");
    try {
      const { data } = await axios.post(
        "/api/users/getfollower",
        {
          id: userState.user.id,
        },
        {
          headers: {
            Authorization: `${userState.jwt}`,
          },
        }
      );

      if (data.error) throw new Error(data.message);

      setFollowers(data.payload);
    } catch (err) {
      setError(err.message);
    }
  };
  return (
    <Box>
      <Typography variant="h4" component="h6" sx={{ textAlign: "center" }}>
        Your Follower
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
          {followers.map((follower) => (
            <Grid
              item
              key={follower.id}
              xs={12}
              sx={{ display: "flex", alignItems: "center", gap: 2 }}
            >
              <Avatar
                alt={follower.firstName + " " + follower.lastName}
                src={follower.imageUrl}
              />
              <Typography component="p" variant="h6">
                {follower.firstName + " " + follower.lastName}
              </Typography>

              <Button
                size="small"
                variant="contained"
                color="info"
                onClick={() =>
                  followProfile(userState.jwt, userDispatch, {
                    follow: follower.id,
                    follower: userState.user.id,
                  })
                }
              >
                👤 Follow
              </Button>
            </Grid>
          ))}
        </Grid>
      )}
    </Box>
  );
}

export default Follower;
