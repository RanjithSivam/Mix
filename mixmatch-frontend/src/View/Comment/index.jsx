import { Box, Button, TextField } from "@mui/material";
import axios from "axios";
import React, { useContext, useState } from "react";
import { UserContext } from "../../context/UserContext";

function Comment({ postId }) {
  const { state: userState } = useContext(UserContext);
  const [comment, setComment] = useState("");

  const addComment = async () => {
    const { data } = await axios.post(
      "/api/comments/insert",
      {
        comment: {
          content: comment,
        },
        id: {
          id: postId,
        },
        userProfileId: {
          id: userState.user.userProfile.id,
        },
      },
      {
        headers: {
          Authorization: `${userState.user.jwt}`,
        },
      }
    );

    console.log(data);
  };
  return (
    <Box componet="form" sx={{ display: "flex", gap: 2 }}>
      <TextField
        variant="outlined"
        type="text"
        placeholder="add your comment"
        value={comment}
        onChange={(e) => setComment(e.target.value)}
        fullWidth
      />
      <Button variant="contained" onClick={addComment}>
        Post
      </Button>
    </Box>
  );
}

export default Comment;
