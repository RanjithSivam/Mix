import {
  Button,
  Card,
  CardActions,
  CardContent,
  Typography,
} from "@mui/material";
import { Box } from "@mui/system";
import axios from "axios";
import React, { useContext, useState, useEffect } from "react";
import { UserContext } from "../../context/UserContext";

function MyPosts() {
  const { state: userState } = useContext(UserContext);
  const [posts, setPosts] = useState([]);

  useEffect(() => {
    myPosts();
  }, []);

  const myPosts = async () => {
    const { data } = await axios.post(
      "/api/posts/myposts",
      {
        id: userState.user.userProfile.id,
      },
      {
        headers: {
          Authorization: `${userState.user.jwt}`,
        },
      }
    );

    setPosts(data.payload);
  };
  return (
    <Box>
      <Typography component="h1" variant="h2">
        My Posts
      </Typography>
      <Box>
        {posts.map((post) => (
          <Card>
            <CardContent>
              <Typography component="h4" variant="g4" gutterBottom>
                {post.userProfile.email}
              </Typography>
              <Typography variant="p" component="div">
                {post.content}
              </Typography>
            </CardContent>
            <CardActions>
              <Button>Love</Button>
              <Button>comment</Button>
              <Button>Share</Button>
            </CardActions>
          </Card>
        ))}
      </Box>
    </Box>
  );
}

export default MyPosts;
