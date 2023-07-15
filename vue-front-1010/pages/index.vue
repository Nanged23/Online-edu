<template>
  <div>
    <!-- 幻灯片 -->
    <SwiperVue />
    <div id="aCoursesList">
      <!-- 网校课程 -->
      <NetCourse />
      <!-- 网校名师 -->
      <NetTeacher />
    </div>
  </div>
</template>

<script>
import SwiperVue from "../components/home/Swiper.vue";
import NetCourse from "../components/home/NetCourse.vue";
import NetTeacher from "../components/home/NetTeacher.vue";
export default {
  components: {
    SwiperVue,
    NetCourse,
    NetTeacher
  }
};
</script>
