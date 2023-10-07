import service from "..";

export default {
  //根据手机号码发送短信
  getMobile(mobile) {
    return service({
      url: `/edumsm/msm/send/${mobile}`,
      method: "get"
    });
  },
  //用户注册
  submitRegister(formItem) {
    return service({
      url: `/api/ucenter/member/register`,
      method: "post",
      data: formItem
    });
  }
};
