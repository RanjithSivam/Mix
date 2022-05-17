import {
  Typography,
  Button,
  List,
  ListItemButton,
  ListItemText,
  ListItemIcon,
  Card,
  Grid,
} from "@mui/material";
import { Box } from "@mui/system";
import React, { useContext } from "react";
import NewspaperOutlinedIcon from "@mui/icons-material/NewspaperOutlined";
import BookmarkOutlinedIcon from "@mui/icons-material/BookmarkOutlined";
import SettingsInputAntennaOutlinedIcon from "@mui/icons-material/SettingsInputAntennaOutlined";
import SignpostOutlinedIcon from "@mui/icons-material/SignpostOutlined";
import GroupAddOutlinedIcon from "@mui/icons-material/GroupAddOutlined";
import AccountCircleOutlinedIcon from "@mui/icons-material/AccountCircleOutlined";
import { Link, Outlet, useNavigate } from "react-router-dom";
import { UserContext } from "../../context/UserContext";
import { LOGOUT } from "../../Util/loginStatus";

const menubar = [
  {
    id: 1,
    name: "newsfeed",
    icon: <NewspaperOutlinedIcon />,
    link: "newsfeed",
  },
  {
    id: 2,
    name: "following",
    icon: <BookmarkOutlinedIcon />,
    link: "following",
  },
  {
    id: 3,
    name: "follower",
    icon: <SettingsInputAntennaOutlinedIcon />,
    link: "follower",
  },
  {
    id: 4,
    name: "my post",
    icon: <SignpostOutlinedIcon />,
    link: "post",
  },
  {
    id: 5,
    name: "profile",
    icon: <AccountCircleOutlinedIcon />,
    link: "",
  },
  {
    id: 6,
    name: "all accounnts",
    icon: <GroupAddOutlinedIcon />,
    link: "all",
  },
];

function Dashboard() {
  const { dispatch: loginDispatch } = useContext(UserContext);
  const navigate = useNavigate();
  return (
    <Box sx={{ px: 10 }}>
      <Typography
        variant="header"
        component="header"
        sx={{
          display: "flex",
          justifyContent: "space-around",
          alignItems: "center",
        }}
      >
        <Typography variant="h1" component="a">
          MixMatch
        </Typography>
        <Button
          color="error"
          variant="outlined"
          onClick={() => {
            loginDispatch({ type: LOGOUT });
            navigate("/");
          }}
        >
          logout
        </Button>
      </Typography>
      <Grid
        container
        spacing={2}
        sx={{
          mt: 2,
        }}
      >
        <Grid item xs={12} sm={4}>
          <Card>
            <List
              sx={{ width: "100%", maxWidth: 250 }}
              component="nav"
              aria-labelledby="nested-list-subheader"
            >
              {menubar.map((menu) => (
                <ListItemButton component={Link} to={menu.link} key={menu.id}>
                  <ListItemIcon>{menu.icon}</ListItemIcon>
                  <ListItemText
                    sx={{ textTransform: "capitalize" }}
                    primary={menu.name}
                  />
                </ListItemButton>
              ))}
            </List>
          </Card>
        </Grid>
        <Grid item sx={12} sm={8}>
          <Outlet />
        </Grid>
      </Grid>
    </Box>
  );
}

export default Dashboard;
