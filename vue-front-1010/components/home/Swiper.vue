<template>
  <div v-swiper:mySwiper="swiperOption">
    <div class="swiper-wrapper">
      
      <div
        v-for="banner in bannerList"
        :key="banner.id"
        class="swiper-slide"
        style="background: #535555;"
      >
        <a target="_blank"   :href="banner.linkUrl">
          <img width="65%"   :src="banner.imageUrl" :alt="banner.title" />
        </a>
      </div>

    </div>
    <div class="swiper-pagination swiper-pagination-white"></div>
    <div
      class="swiper-button-prev swiper-button-white"
      slot="button-prev"
    ></div>
    <div
      class="swiper-button-next swiper-button-white"
      slot="button-next"
    ></div>
  </div>
</template>

<script>
import banner from "../../request/home/banner";
export default {
  data() {
    return {
      //banner数组
      bannerList: [],
      swiperOption: {
        loop: true,
        //配置分页
        pagination: {
          el: ".swiper-pagination" //分页的dom节点
        },
        //配置导航
        navigation: {
          nextEl: ".swiper-button-next", //下一页dom节点
          prevEl: ".swiper-button-prev" //前一页dom节点
        }
      }
    };
  },
  created() {
    //调用查询banner查询的方法
    this.getBannerList();
  },
  methods: {
    getBannerList(){
      banner.getListBanner().then(response => {
        this.bannerList = response.data.data.list;
        console.log(response.data.data.list);
      });
    }
  }
};
</script>
