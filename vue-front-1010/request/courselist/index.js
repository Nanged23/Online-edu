import service from "..";
export default {
  getPageList(page, limit, searchObj) {
    return service({
      url: `/eduservice/coursefront/getFrontCourseList/${page}/${limit}`,
      method: "post",
      data: searchObj
    });
  },
  // 获取课程二级分类
  getNestedTreeList2() {
    return service({
      url: `/eduservice/edu/course/list2`,
      method: "get"
    });
  },
  getById(courseId) {
    return service({
      url: `/eduservice/coursefront/getFrontCourseInfo/${courseId}`,
      method: "get"
    });
  }
};
