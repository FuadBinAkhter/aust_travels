import { Navigate, useRoutes } from "react-router-dom";
import Users from "./pages/Users";
import HomePage from "./pages/HomePage/home";
import ViewBusPage from "./pages/SelectBus/modal";
import SemesterPage from "./pages/Semester/semester";
import DepartmentPage from "./pages/Department/department";
// layouts
// import DashboardLayout from './layouts/dashboard';
// import LogoOnlyLayout from './layouts/LogoOnlyLayout';
// //
// import Login from './pages/Login';
// import Register from './pages/Register';
// import DashboardApp from './pages/DashboardApp';
// import Products from './pages/Products';
// import Blog from './pages/Blog';
// import User from './pages/User';
// import NotFound from './pages/Page404';

// ----------------------------------------------------------------------

export default function Router() {
  return useRoutes([
    {
      path: "/users",
      element: <Users/>,
    },
    {
      path: "/",
      element: <HomePage/>
    },
    {
      path: "/viewbus",
      element: <ViewBusPage/>
    },
    {
      path: "/semseter",
      element: <SemesterPage/>
    },
    {
      path: "/department",
      element: <DepartmentPage/>
    }

    // {
    //   path: "/",
    //   element: <LogoOnlyLayout />,
    //   children: [
    //     { path: "login", element: <Login /> },
    //     { path: "register", element: <Register /> },
    //     { path: "404", element: <NotFound /> },
    //     { path: "/", element: <Navigate to="/dashboard" /> },
    //     { path: "*", element: <Navigate to="/404" /> },
    //   ],
    // },
    // { path: "*", element: <Navigate to="/404" replace /> },
  ]);
}
