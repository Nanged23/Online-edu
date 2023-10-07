import service from "..";

export default {
  getList() {
    return service({
      url: `/educms/bannerfront/getAllBanner`,
      method: "GET"
    });
  }
};
