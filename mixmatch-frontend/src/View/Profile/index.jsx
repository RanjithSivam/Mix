import {
  Card,
  CardActions,
  CardContent,
  CardMedia,
  Grid,
  TextField,
} from "@mui/material";
import { Button } from "@mui/material";
import { createTheme, ThemeProvider } from "@mui/material/styles";

import React, { useState } from "react";

import { useUser } from "../../context/User/UserState";
import { updateUser } from "../../context/User/UserActions";

const theme = createTheme();

function Profile() {
  const [userState, userDispatch] = useUser();
  const [dob, setDob] = useState(userState.user.dateOfBirth);

  const [first, setFirst] = useState(userState.user.firstName);
  const [last, setLast] = useState(
    userState.user.lastName ? userState.user.lastName : ""
  );
  const [image, setImage] = useState(
    userState.user.imageUrl ? userState.user.imageUrl : ""
  );
  const [about, setAbout] = useState(
    userState.user.about ? userState.user.about : ""
  );
  const [user, setUser] = useState(
    userState.user.username ? userState.user.username : ""
  );

  console.log(userState);

  const handleSubmit = (e) => {
    e.preventDefault();
    updateUser(userDispatch, userState.jwt, {
      email: userState.user.email,
      firstName: first,
      lastName: last,
      username: user,
      dateOfBirth: dob,
      imageUrl: image,
      about: about,
      id: userState.user.id,
    });
  };

  const handleReset = () => {
    setFirst(userState.user.firstName ? userState.user.firstName : "");
    setLast(userState.user.lastName ? userState.user.lastName : "");
    setDob(userState.user.dateOfBirth);
    setImage(userState.user.imageUrl ? userState.user.imageUrl : "");
    setAbout(userState.user.about ? userState.user.about : "");
    setUser(userState.user.username ? userState.user.username : "");
  };

  return (
    <ThemeProvider theme={theme}>
      <Card
        sx={{
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          maxWidth: "500px",
          mx: "auto",
        }}
      >
        <CardMedia
          image={image}
          sx={{
            width: "100%",
            height: "200px",
          }}
        />
        <CardContent>
          <Grid container spacing={2}>
            <Grid item xs={12}>
              <TextField
                name="userName"
                fullWidth
                label="User Name"
                autoFocus
                value={user}
                onChange={(e) => setUser(e.target.value)}
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                autoComplete="given-name"
                name="firstName"
                required
                fullWidth
                id="firstName"
                label="First Name"
                value={first}
                onChange={(e) => setFirst(e.target.value)}
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                required
                fullWidth
                id="lastName"
                label="Last Name"
                name="lastName"
                value={last}
                onChange={(e) => setLast(e.target.value)}
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                required
                fullWidth
                id="email"
                label="Email Address"
                name="email"
                autoComplete="email"
                value={userState.user.email}
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                required
                fullWidth
                name="createdAt"
                label="Date Of Birth"
                type="date"
                value={userState.user.createdAt}
                input={dob}
                onChange={(value) => setDob(value)}
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                required
                fullWidth
                name="createdAt"
                label="Joined At"
                type="date"
                value={userState.user.createdAt}
                disabled
                autoFocus
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                placeholder="About"
                fullWidth
                multiline
                rows={2}
                maxRows={4}
                value={about}
                onChange={(e) => setAbout(e.target.value)}
              />
            </Grid>
          </Grid>
        </CardContent>
        <CardActions>
          <Button
            color="primary"
            variant="outlined"
            type="submit"
            onClick={handleSubmit}
          >
            Update
          </Button>
          <Button color="secondary" variant="outlined" onClick={handleReset}>
            Reset
          </Button>
        </CardActions>
      </Card>
    </ThemeProvider>
  );
}

export default Profile;
