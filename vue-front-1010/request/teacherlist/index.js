import service from "..";

export default {
  getPageList(page, limit) {
    return service({
      url: `/eduservice/teacherfront/getTeacherFrontList/${page}/${limit}`,
      method: "post"
    });
  },
  getById(teacherId) {
    return service({
      url: `/eduservice/teacherfront/getTeacherFrontInfo/${teacherId}`,
      method: "get"
    });
  }
};
