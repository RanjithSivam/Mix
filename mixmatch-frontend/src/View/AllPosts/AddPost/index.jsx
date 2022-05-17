import {
  Avatar,
  Box,
  Button,
  Card,
  CardContent,
  TextField,
  Typography,
} from "@mui/material";
import axios from "axios";
import React, { useContext, useState } from "react";
import { useUser } from "../../../context/User/UserState";

function AddPost() {
  const [userState, userDispatch] = useUser();

  const [content, setContent] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    addPost();
  };

  const addPost = async () => {
    const { data } = await axios.post(
      "/api/posts/add",
      {
        content: content,
        userProfile: userState.user,
      },
      {
        headers: {
          Authorization: userState.jwt,
        },
      }
    );
  };
  return (
    <Card>
      <CardContent>
        <Box sx={{ display: "flex", alignItems: "center", mb: 2 }}>
          <Avatar
            alt={userState.user.firstName + " " + userState.user.lastName}
            src={userState.user.imageUrl}
            sx={{
              mr: 2,
            }}
          />
          <Typography component="p" variant="h5">
            {userState.user.firstName + " " + userState.user.lastName}
          </Typography>
        </Box>
        <Box
          component="form"
          onSubmit={handleSubmit}
          sx={{ display: "flex", flexDirection: "column", gap: 2 }}
        >
          <TextField
            placeholder="What is happening?"
            multiline
            rows={5}
            value={content}
            onChange={(e) => setContent(e.target.value)}
            maxLength={200}
            fullWidth
          />
          <Box sx={{ display: "flex", justifyContent: "space-between" }}>
            <Button size="small" variant="contained" component="label">
              📷 Upload Image
              <input type="file" hidden />
            </Button>
            <Box sx={{ display: "flex", alignItems: "center", gap: 1 }}>
              <Typography
                component="p"
                variant="small"
                sx={{ fontWeight: "bold" }}
              >
                Characters: {content.length}/200
              </Typography>
              <Button variant="contained" type="submit" color="success">
                Post 📨
              </Button>
            </Box>
          </Box>
        </Box>
      </CardContent>
    </Card>
  );
}

export default AddPost;
