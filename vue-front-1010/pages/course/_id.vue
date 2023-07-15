<template>
  <client-only>
    <div id="aCoursesList" class="bg-fa of">
      <!-- /课程详情 开始 -->
      <section class="container">
        <section class="path-wrap txtOf hLh30">
          <a href="#" title class="c-999 fsize14">首页</a>
          \
          <a href="/course" title class="c-999 fsize14">课程列表</a>
          \
          <span class="c-333 fsize14">{{ course.subjectLevelOne }}</span>
          \
          <span class="c-333 fsize14">{{ course.subjectLevelTwo }}</span>
        </section>
        <div>
          <article class="c-v-pic-wrap" style="height: 357px;">
            <section id="videoPlay" class="p-h-video-box">
              <img
                :src="course.cover"
                :alt="course.title"
                class="dis c-v-pic"
              />
            </section>
          </article>
          <aside class="c-attr-wrap">
            <section class="ml20 mr15">
              <h2 class="hLh30 txtOf mt15">
                <span class="c-fff fsize24">{{ course.title }}</span>
              </h2>
              <section class="c-attr-jg">
                <span class="c-fff">价格：</span>
                <b class="c-yellow" style="font-size:24px;"
                  >￥{{ course.price }}</b
                >
              </section>
              <section class="c-attr-mt c-attr-undis">
                <span class="c-fff fsize14"
                  >主讲： {{ course.teacherName }}&nbsp;&nbsp;&nbsp;</span
                >
              </section>
              <section class="c-attr-mt of">
                <span class="ml10 vam">
                  <em class="icon18 scIcon" />
                  <a class="c-fff vam" title="收藏" href="#">收藏</a>
                </span>
              </section>
              <section
                v-if="isbuy || Number(course.price) === 0"
                class="c-attr-mt"
              >
                <a
                  :href="`/player/${defaultId}`"
                  target="_blank"
                  title="立即观看"
                  class="comm-btn c-btn-3"
                  >立即观看</a
                >
              </section>
              <section v-else class="c-attr-mt">
                <a
                  href="#"
                  title="立即购买"
                  class="comm-btn c-btn-3"
                  @click="createOrder()"
                  >立即购买</a
                >
              </section>
            </section>
          </aside>
          <aside class="thr-attr-box">
            <div id="mount">
              <div class="content">
                购买数
                <div>{{ course.buyCount }}</div>
              </div>
              <div class="content">
                课时数
                <div>{{ course.lessonNum }}</div>
              </div>
              <div class="content">
                浏览数
                <div>{{ course.viewCount }}</div>
              </div>
            </div>
          </aside>
          <div class="clear" />
        </div>
        <!-- /课程封面介绍 -->
        <div class="mt20 c-infor-box">
          <article class="fl col-7">
            <section class="mr30">
              <div class="i-box">
                <div>
                  <section
                    id="c-i-tabTitle"
                    class="c-infor-tabTitle c-tab-title"
                  >
                    <a name="c-i" class="current" title="课程详情">课程详情</a>
                  </section>
                </div>
                <article class="ml10 mr10 pt20">
                  <div>
                    <h6 class="c-i-content c-infor-title">
                      <span>课程介绍</span>
                    </h6>
                    <div class="course-txt-body-wrap">
                      <section class="course-txt-body">
                        <!-- 将内容中的html翻译过来 -->
                        <p v-html="course.description">
                          {{ course.description }}
                        </p>
                      </section>
                    </div>
                  </div>
                  <!-- /课程介绍 -->
                  <div class="mt50">
                    <h6 class="c-g-content c-infor-title">
                      <span>课程大纲</span>
                    </h6>
                    <section class="mt20">
                      <div class="lh-menu-wrap">
                        <menu id="lh-menu" class="lh-menu mt10 mr10">
                          <ul>
                            <!-- 课程章节目录 -->
                            <li
                              v-for="chapter in chapterVideoList"
                              :key="chapter.id"
                              class="lh-menu-stair"
                            >
                              <a title="chapter.title" class="current-1">
                                <em class="lh-menu-i-1 icon18 mr10" />{{
                                  chapter.title
                                }}
                              </a>
                              <ol class="lh-menu-ol" style="display: block;">
                                <li
                                  v-for="video in chapter.children"
                                  :key="video.id"
                                  class="lh-menu-second ml30"
                                >
                                  <a
                                    @click = gotoWatch(video.videoSourceId)
                                    class="toPlayA"
                                    target="_blank"
                                    title
                                  >
                                    <span v-if="video.free === true" class="fr">
                                      <i class="free-icon vam mr10">免费试听</i>
                                    </span>
                                    <em class="lh-menu-i-2 icon16 mr5"
                                      >&nbsp;</em
                                    >{{ video.title }}
                                  </a>
                                </li>
                              </ol>
                            </li>
                          </ul>
                        </menu>
                      </div>
                    </section>
                  </div>
                  <!-- /课程大纲 -->
                </article>
              </div>
            </section>
          </article>
          <aside class="fl col-3">
            <div class="i-box">
              <div>
                <section class="c-infor-tabTitle c-tab-title">
                  <a title href="javascript:void(0)">主讲讲师</a>
                </section>
                <section class="stud-act-list">
                  <ul style="height: auto;">
                    <li>
                      <div class="u-face">
                        <a
                          :href="'/teacher/' + course.teacherId"
                          target="_blank"
                        >
                          <img
                            :src="course.avatar"
                            width="50"
                            height="50"
                            alt
                          />
                        </a>
                      </div>
                      <section class="hLh30 txtOf">
                        <a
                          :href="'/teacher/' + course.teacherId"
                          class="c-333 fsize16 fl"
                          target="_blank"
                          >{{ course.teacherName }}</a
                        >
                      </section>
                      <section class="hLh20 txtOf">
                        <span class="c-999">{{ course.intro }}</span>
                      </section>
                    </li>
                  </ul>
                </section>
              </div>
            </div>
          </aside>
          <div class="clear"></div>
        </div>
      </section>
      <!-- /课程详情 结束 -->
    </div>
  </client-only>
</template>

<script>
import course from "../../request/courselist";
import order from "../../request/order";
import cookie from "js-cookie";
export default {
  asyncData({ params, error }) {
    return { courseId: params.id };
  },
  data() {
    return {
      course: {},
      chapterVideoList: [],
      isbuy: false,
      defaultId: ""
    };
  },
  created() {
    this.initCourseInfo();
  },
  methods: {
    //初始化课程信息
    initCourseInfo() {
      course.getById(this.courseId).then(response => {
        console.log(response.data);
        this.course = response.data.data.courseWebVo;
        this.chapterVideoList = response.data.data.chapterVideoList;
        this.isbuy = response.data.data.isBuy;
        console.log(Number(this.course.price) );
        this.defaultId = this.chapterVideoList[0].children[0].videoSourceId;
      });
    },

    //根据课程id，调用接口方法生成订单
    createOrder() {
      let user = cookie.get("guli_ucenter");

      order.createOrder(this.courseId).then(response => {
        if (response == undefined) {
          window.location.href = "/sign/login";
          return;
        }
        if (response.data.success) {
          //订单创建成功，跳转到订单页面
          this.$router.push({ path: "/order/" + response.data.data.orderId });
        }
      });
    },
    goVideo() {
      this.$router.push();
    },
    gotoWatch(id){
      if(this.isbuy == true||Number(this.course.price)==0 ) {
        this.$router.push({ path: `/player/${id}`});
      }else{
        this.$message({
              type: "warning",
              message: "尚未购买~ 即将跳转到购买页面"
            });
        this.createOrder()
      }
    }
  }
};
</script>

<style scoped>
#mount {
  display: flex;
  flex-direction: column;
  height: 100%;
}
#mount > .content {
  height: 33%;
  color: white;
  text-align: center;
  margin-top: 10px;
}
.toPlayA{
  cursor: pointer;
}
</style>
