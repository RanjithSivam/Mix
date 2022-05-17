import {
  Avatar,
  Box,
  Button,
  Card,
  CardActions,
  CardContent,
  Typography,
} from "@mui/material";
import axios from "axios";
import React, { useContext, useEffect, useState } from "react";
import { useUser } from "../../context/User/UserState";
import { UserContext } from "../../context/UserContext";
import Comment from "../Comment";
import AddPost from "./AddPost";

function AllPosts() {
  const [userState, userDispatch] = useUser();
  const [posts, setPosts] = useState([]);
  const [showComment, setShowComment] = useState(false);

  useEffect(() => {
    allPosts();
  }, []);

  const allPosts = async () => {
    const { data } = await axios.post(
      "/api/posts/followingposts",
      {
        id: userState.user.id,
      },
      {
        headers: {
          Authorization: userState.jwt,
        },
      }
    );

    console.log(data);
    setPosts(data.payload);
  };

  const lovePost = async (postId) => {
    const { data } = await axios.post(
      "/api/posts/lovepost",
      {
        postId: postId,
        likedUserId: userState.user.id,
      },
      {
        headers: {
          Authorization: userState.jwt,
        },
      }
    );

    setPosts(
      posts.map((post) => (data.payload.id == post.id ? data.payload : post))
    );
  };

  const sharePost = async (postId) => {
    const { data } = await axios.post(
      "/api/posts/sharepost",
      {
        postId: postId,
        likedUserId: userState.user.id,
      },
      {
        headers: {
          Authorization: userState.jwt,
        },
      }
    );

    setPosts([...posts, data.payload]);
  };
  return (
    <Box>
      <AddPost />
      <Box sx={{ mt: 2 }}>
        {posts.map((post) => (
          <Card>
            <CardContent
              sx={{ display: "flex", flexDirection: "column", gap: 2 }}
            >
              <Box sx={{ display: "flex", gap: 2, alignItems: "center" }}>
                <Avatar
                  alt={
                    post.userProfile.firstName + " " + post.userProfile.lastName
                  }
                  src={post.userProfile.imageUrl}
                />
                <Typography component="p" variant="h6">
                  {post.userProfile.firstName + " " + post.userProfile.lastName}
                </Typography>
              </Box>
              <Typography variant="h5" component="div">
                {post.content}
              </Typography>
            </CardContent>
            <CardActions>
              <Button onClick={() => lovePost(post.id)}>
                {post.love.length} Love
              </Button>
              <Button onClick={() => setShowComment(!showComment)}>
                {post.comment.length} comment
              </Button>
              <Button onClick={() => sharePost(post.id)}>
                {post.share.length} Share
              </Button>
            </CardActions>
            <CardContent>
              {showComment && <Comment postId={post.id} />}
              {post.comment.length > 0 && (
                <Box>
                  {post.comment.map((comm) => (
                    <Card key={comm.id}>
                      <CardContent>
                        <img
                          src={comm.userProfile.imageUrl}
                          alt="profile-pic"
                        />
                        <Typography component="small">
                          {comm.userProfile.email}
                        </Typography>
                        <Typography component="p" variant="h4">
                          {comm.content}
                        </Typography>
                      </CardContent>
                    </Card>
                  ))}
                </Box>
              )}
            </CardContent>
          </Card>
        ))}
      </Box>
    </Box>
  );
}

export default AllPosts;
