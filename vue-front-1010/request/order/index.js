import service from "..";
export default {
  //1、创建订单
  createOrder(cid) {
    return service({
      url: `/eduorder/order/createOrder/${cid}`,
      method: "post"
    });
  },
  //2、根据id获取订单
  getById(cid) {
    return service({
      url: `/eduorder/order/getOrderInfo/${cid}`,
      method: "get"
    });
  },
  //3、生成微信支付二维码
  createNative(cid) {
    return service({
      url: `/eduorder/paylog/createNative/${cid}`,
      method: "get"
    });
  },
  //4、根据id获取订单支付状态
  queryPayStatus(cid) {
    return service({
      url: `/eduorder/paylog/queryPayStatus/${cid}`,
      method: "get"
    });
  }
};
