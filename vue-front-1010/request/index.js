import axios from "axios";
import { MessageBox, Message } from "element-ui";
import cookie from "js-cookie";
// 创建axios实例
const service = axios.create({
  // baseURL: "http://localhost:8160", // api的base_url
  // baseURL: "http://127.0.0.1:4523/m1/2669739-0-default",
  baseURL: "http://localhost:8160/",
  timeout: 10000 // 请求超时时间
});

// http request 拦截器
service.interceptors.request.use(
  config => {
    //debugger
    if (cookie.get("guli_token")) {
      config.headers["token"] = cookie.get("guli_token");
    }
    console.log("请求参数:", config);
    return config;
  },
  err => {
    return Promise.reject(err);
  }
);
// http response 拦截器
service.interceptors.response.use(
  response => {
    //debugger
    if (response.data.code == 28004) {
      console.log("response.data.resultCode是28004");
      // 返回 错误代码-1 清除ticket信息并跳转到登录页面
      //debugger
      window.location.href = "/sign/login";
      return;
    } else {
      if (response.data.code !== 20000) {
        //25000：订单支付中，不做任何提示
        if (response.data.code != 25000) {
          Message({
            message: response.data.message || "error",
            type: "error",
            duration: 5 * 1000
          });
        }
      } else {
        return response;
      }
    }
  },
  error => {
    return Promise.reject(error.response); // 返回接口返回的错误信息
  }
);

export default service;
