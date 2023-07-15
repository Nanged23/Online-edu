import service from "..";

export default {
  getList() {
    return service({
      url: `/eduservice/indexfront/index`,
      method: "get"
    });
  }
};
