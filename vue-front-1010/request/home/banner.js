import service from "..";

export default {
  getListBanner() {
    return service({
      url: `/educms/bannerfront/getAllBanner`,
      method: "GET"
    });
  }
};
