<template>
  <div>
    <section class="container">
      <header class="comm-title">
        <h2 class="tac">
          <span class="c-333">热门课程</span>
        </h2>
      </header>
      <div>
        <article class="comm-course-list">
          <ul class="of" id="bna">
            <li v-for="(course, index) in courseList" v-bind:key="index">
              <div class="cc-l-wrap">
                <section class="course-img">
                  <!-- ~/assets/photo/course/01.jpg -->
                  <img
                    :src="course.cover"
                    class="img-responsive"
                    :alt="course.title"
                  />
                  <div class="cc-mask">
                    <a
                      :href="'/course/' + course.id"
                      title="开始学习"
                      class="comm-btn c-btn-1"
                      >开始学习</a
                    >
                  </div>
                </section>
                <h3 class="hLh30 txtOf mt10">
                  <a
                    href="#"
                    :title="course.title"
                    class="course-title fsize18 c-333"
                    >{{ course.title }}</a
                  >
                </h3>
                <section class="mt10 hLh20 of">
                  <span
                    class="fr jgTag bg-green"
                    v-if="Number(course.price) === 0"
                  >
                    <i class="c-fff fsize12 f-fA">免费</i>
                  </span>
                  <span class="fr jgTag bg-green" v-else>
                    <i class="c-fff fsize12 f-fA"> ￥{{ course.price }}</i>
                  </span>
                  <span class="fl jgAttr c-ccc f-fA">
                    <i class="c-999 f-fA">{{ course.buyCount }} 人学习</i>
                    |
                    <i class="c-999 f-fA">{{ course.viewCount }} 人浏览</i>
                  </span>
                </section>
              </div>
            </li>
          </ul>
          <div class="clear"></div>
        </article>
        <section class="tac pt20">
          <a href="#" title="全部课程" class="comm-btn c-btn-2">全部课程</a>
        </section>
      </div>
    </section>
  </div>
</template>
<script>
import homedata from "../../request/home/courseteacher";
export default {
  data() {
    return {
      courseList: {}
    };
  },
  created() {
    this.getCourseList();
  },
  methods: {
    getCourseList: function() {
      homedata.getList().then(response => {
        this.courseList = response.data.data.eduList;
      });
    }
  }
};
</script>

<style lang="css" scoped>
.img-responsive {
  width: 220px;
  height: 125px;
}
</style>
