import { BrowserRouter, Route, Routes } from "react-router-dom";
import CombinedContextProvider from "./context/CombinedContextProvider";
import UserContextProvider from "./context/UserContext";
import AllPosts from "./View/AllPosts";
import AllProfile from "./View/AllProfile";
import Dashboard from "./View/Dashboard";
import Follower from "./View/Follower";
import Following from "./View/Following";
import Landing from "./View/Landing";
import Login from "./View/Login";
import MyPosts from "./View/MyPosts";
import Profile from "./View/Profile";
import Signup from "./View/Signup";

const routes = [
  {
    path: "newsfeed",
    element: <AllPosts />,
  },
  {
    path: "following",
    element: <Following />,
  },
  {
    path: "follower",
    element: <Follower />,
  },
  {
    path: "post",
    element: <MyPosts />,
  },
  {
    path: "",
    element: <Profile />,
  },
  {
    path: "all",
    element: <AllProfile />,
  },
];

function App() {
  return (
    <BrowserRouter>
      <UserContextProvider>
        <CombinedContextProvider>
          <Routes>
            <Route path="/" element={<Landing />} />
            <Route path="signup" element={<Signup />} />
            <Route path="login" element={<Login />} />
            <Route path="dashboard" element={<Dashboard />}>
              {routes.map((route) => (
                <Route path={route.path} element={route.element} />
              ))}
            </Route>
          </Routes>
        </CombinedContextProvider>
      </UserContextProvider>
    </BrowserRouter>
  );
}

export default App;
