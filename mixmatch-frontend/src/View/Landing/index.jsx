import { Box, Button, Typography } from "@mui/material";
import React from "react";
import { Link } from "react-router-dom";

function Landing() {
  return (
    <Box
      sx={{
        height: "100vh",
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        flexDirection: "column",
      }}
    >
      <Typography variant="h1" component="h1">
        MixMatch
      </Typography>
      <Box
        sx={{
          display: "flex",
          gap: 2,
        }}
      >
        <Button
          component={Link}
          variant="contained"
          color="primary"
          to="signup"
        >
          Signup
        </Button>
        <Button component={Link} variant="contained" color="primary" to="login">
          Login
        </Button>
      </Box>
    </Box>
  );
}

export default Landing;
