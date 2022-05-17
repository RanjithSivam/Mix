import { Avatar, Box, Button, Grid, Typography } from "@mui/material";
import React, { useEffect } from "react";
import {
  fetchAllProfile,
  followProfile,
  unFollowProfile,
} from "../../context/Profile/ProfileActions";
import { useProfile } from "../../context/Profile/ProfileState";
import { useUser } from "../../context/User/UserState";

function AllProfile() {
  const [userState, userDispatch] = useUser();
  const [profileState, profileDispatch] = useProfile();

  useEffect(() => {
    fetchAllProfile(userState.jwt, profileDispatch);
  }, []);

  return (
    <Box>
      <Typography variant="h4" component="h6" sx={{ textAlign: "center" }}>
        All Profile
      </Typography>
      <Grid container spacing={2} sx={{ mt: 2 }}>
        {profileState.profiles
          .filter((profile) => profile.id !== userState.user.id)
          .map((profile) => (
            <Grid
              item
              key={profile.id}
              xs={12}
              sx={{ display: "flex", alignItems: "center", gap: 2 }}
            >
              <Avatar
                alt={profile.firstName + " " + profile.lastName}
                src={profile.imageUrl}
              />
              <Typography component="p" variant="h6">
                {profile.firstName + " " + profile.lastName}
              </Typography>
              {!userState.user.following.includes(profile.id) ? (
                <Button
                  size="small"
                  variant="contained"
                  color="info"
                  onClick={() =>
                    followProfile(userState.jwt, userDispatch, {
                      follow: profile.id,
                      follower: userState.user.id,
                    })
                  }
                >
                  👤 Follow
                </Button>
              ) : (
                <Button
                  size="small"
                  variant="contained"
                  color="success"
                  onClick={() =>
                    unFollowProfile(userState.jwt, userDispatch, {
                      follow: profile.id,
                      follower: userState.user.id,
                    })
                  }
                >
                  ✔️ Followed
                </Button>
              )}
            </Grid>
          ))}
      </Grid>
    </Box>
  );
}

export default AllProfile;
