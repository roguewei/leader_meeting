const Login = () => import("../views/Login/Login");
const Home = () => import("../views/Home/Home");
const Welcome = () => import("../views/Home/children/Welcome");
const User = () => import("../views/Home/children/User");
const Permission = () => import("../views/Home/children/Permission");
const Role = () => import("../views/Home/children/Role");
const Leader = () => import("../views/Home/children/Leader");
const Meetingroom = () => import("../views/Home/children/Meetingroom");
const Excel = () => import("../views/Home/children/Excel");
const Parameter = () => import("../views/Home/children/Parameter");
const Meeting = () => import("../views/Home/children/Meeting");

const routes = [
  {
    path: "/",
    redirect: "/login"
  },
  {
    path: "/login",
    component: Login
  },
  {
    path: "/home",
    component: Home,
    // 当来到/home页面时进行重定向
    redirect: "/welcome",
    children: [
      {
        path: "/welcome",
        component: Welcome
      },
      {
        path: "/user",
        component: User
      },
      {
        path: "/parameter",
        component: Parameter
      },
      {
        path: "/permission",
        component: Permission
      },
      {
        path: "/role",
        component: Role
      },
      {
        path: "/leader",
        component: Leader
      },
      {
        path: "/meetingroom",
        component: Meetingroom
      },
      {
        path: "/meeting",
        component: Meeting
      },
      {
        path: "/excel",
        component: Excel
      }
    ]
  }
];

export default routes;
