import axios from "axios";
import { Message } from "element-ui";
import router from "../router/index";
import net from "../common/const";
axios.defaults.withCredentials = true;

export function request(config) {
  const instance = axios.create({
    baseURL: net.BASE_URL,
    // baseURL: "http://120.79.89.24:8001",
    timeout: 10000
  });

  instance.interceptors.request.use(config => {
    // 为请求头对象添加Token验证的Authorization字段
    config.headers.authorization = window.sessionStorage.getItem("token");
    return config;
  });

  instance.interceptors.response.use(config => {
    if (
      config.data.status === 10000 ||
      config.data.status === 500205 ||
      config.data.status === 500105
    ) {
      Message({
        message: config.data.msg,
        type: "error"
      });
      // 重定向
      router.push("/login");
    }

    return config.data;
  });

  return instance(config);
}
