import request from "@/utils/request";

const api_name = "/staservice/sta";
export default {
  createStatistics(day) {
    return request({
      url: `${api_name}/registerCount/${day}`,
      method: "post"
    });
  },
  showChart(searchObj) {
    return request({
      url: `${api_name}/showData/${searchObj.type}/${searchObj.begin}/${
        searchObj.end
      }`,
      method: "get"
    });
  }
};
