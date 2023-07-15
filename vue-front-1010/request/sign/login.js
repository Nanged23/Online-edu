import service from "..";
import cookie from "js-cookie";
export default {
  //登录
  submitLogin(userInfo) {
    return service({
      url: `/api/ucenter/member/login`,
      method: "post",
      data: userInfo
    });
  },
  //根据token获取用户信息
  getLoginInfo() {
    return service({
      url: `/api/ucenter/member/getMemberInfo/`,
      method: "get",
      headers: { token: cookie.get("guli_token") }
    });
    //headers: {'token': cookie.get('guli_token')}
  },
  wxLogin() {
    return service({
      url: `/api/ucenter/wx/login`,
      method: "get"
    });
  }
};
