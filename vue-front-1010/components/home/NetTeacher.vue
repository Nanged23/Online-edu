<template>
  <div>
    <section class="container">
      <header class="comm-title">
        <h2 class="tac">
          <span class="c-333">名师大咖</span>
        </h2>
      </header>
      <div>
        <article class="i-teacher-list">
          <ul class="of">
            <li v-for="(teacher, index) in teacherList" v-bind:key="index">
              <section class="i-teach-wrap">
                <div class="i-teach-pic">
                  <a :href="'/teacher/' + teacher.id" :title="teacher.name">
                    <img
                      :alt="teacher.name"
                      :src="teacher.avatar"
                      class="avatar"
                    />
                  </a>
                </div>
                <div class="mt10 hLh30 txtOf tac">
                  <a
                    :href="'/teacher/' + teacher.id"
                    :title="teacher.name"
                    class="fsize18 c-666"
                    >{{ teacher.name }}</a
                  >
                </div>
                <div class="hLh30 txtOf tac">
                  <span class="fsize14 c-999">{{ teacher.intro }}</span>
                </div>
                <div class="mt15 i-q-txt">
                  <p class="c-999 f-fA">{{ teacher.career }}</p>
                </div>
              </section>
            </li>
          </ul>
          <div class="clear"></div>
        </article>
        <section class="tac pt20">
          <a href="/teacher" title="全部讲师" class="comm-btn c-btn-2">全部讲师</a>
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
      teacherList: []
    };
  },
  created() {
    this.getTeacherList();
  },
  methods: {
    getTeacherList: function() {
      homedata.getList().then(response => {
        this.teacherList = response.data.data.teacherList;
      });
    }
  }
};
</script>

<style lang="css" scoped>
.avatar {
  margin:0 auto;
  width: 80px;
  height: 80px;
}
</style>
