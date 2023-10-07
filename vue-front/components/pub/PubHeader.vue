<template>
  <header id="header">
    <section class="container">
      <h1 id="logo">
        <a href="#" title="谷粒学院">
          <img src="~/assets/img/logo.png" width="100%" alt="谷粒学院" />
        </a>
      </h1>
      <div class="h-r-nsl">
        <ul class="nav">
          <router-link to="/" tag="li" active-class="current" exact>
            <a>首页</a>
          </router-link>
          <router-link to="/course" tag="li" active-class="current">
            <a>课程</a>
          </router-link>
          <router-link to="/teacher" tag="li" active-class="current">
            <a>名师</a>
          </router-link>
        </ul>
        <!-- / nav -->
        <ul class="h-r-login">
          <li v-if="!loginInfo.id" id="no-login">
            <a href="/sign/login" title="登录">
              <em class="icon18 login-icon">&nbsp;</em>
              <span class="vam ml5">登录</span>
            </a>
            |
            <a href="/sign/register" title="注册">
              <span class="vam ml5">注册</span>
            </a>
          </li>
          <li v-if="loginInfo.id" id="is-login-one" class="mr10">
            <a id="headerMsgCountId" href="#" title="消息">
              <em class="icon18 news-icon">&nbsp;</em>
            </a>
            <q class="red-point" style="display: none">&nbsp;</q>
          </li>
          <li v-if="loginInfo.id" id="is-login-two" class="h-r-user">
            <a href="/ucenter" title>
              <img
                :src="loginInfo.avatar"
                width="30"
                height="30"
                class="vam picImg"
                alt
              />
              <span id="userName" class="vam disIb" style="width: 120px;">{{
                loginInfo.nickname
              }}</span>
            </a>
            <a
              href="javascript:void(0);"
              title="退出"
              @click="logout()"
              class="ml5"
              >退出</a
            >
          </li>
          <!-- /未登录显示第1 li；登录后显示第2，3 li -->
        </ul>
        <aside class="h-r-search">
          <form action="#" method="post">
            <label class="h-r-s-box">
              <input
                type="text"
                placeholder="输入你想学的课程"
                name="queryCourse.courseName"
                value
              />
              <button type="submit" class="s-btn">
                <em class="icon18">&nbsp;</em>
              </button>
            </label>
          </form>
        </aside>
      </div>
      <aside class="mw-nav-btn">
        <div class="mw-nav-icon"></div>
      </aside>
      <div class="clear"></div>
    </section>
  </header>
</template>

<script>
import "~/assets/css/reset.css";
import "~/assets/css/theme.css";
import "~/assets/css/global.css";
import "~/assets/css/web.css";

import cookie from "js-cookie";
import userApi from "../../request/sign/login";
export default {
  data() {
    return {
      token: "",
      loginInfo: {
        id: "",
        age: "",
        avatar: "",
        mobile: "",
        nickname: "",
        sex: ""
      }
    };
  },
  created() {
    this.token = this.$route.query.token;
    if (this.token) {
      this.wxLogin();
    }
    this.showInfo();
  },
  methods: {
    showInfo() {
      // debugger;
      var jsonStr = cookie.get("guli_ucenter");
      console.log(jsonStr);
      //alert(jsonStr)
      if (jsonStr) {
        this.loginInfo = JSON.parse(jsonStr);
      }
    },

    logout() {
      //debugger
      cookie.set("guli_ucenter", "", { domain: "localhost" });
      cookie.set("guli_token", "", { domain: "localhost" });

      //跳转页面
      window.location.href = "/";
    },

    wxLogin() {
      if (this.token == "") return;
      //把token存在cookie中、也可以放在localStorage中
      cookie.set("guli_token", this.token, { domain: "localhost" });
      cookie.set("guli_ucenter", "", { domain: "localhost" });
      //登录成功根据token获取用户信息
      userApi.getLoginInfo().then(response => {
        this.loginInfo = response.data.data.userInfo;
        console.log(response.data.data);
        //将用户信息记录cookie
        cookie.set("guli_ucenter", this.loginInfo, { domain: "localhost" });
      });
    }
  }
};
</script>

<style lang="css" scoped>
.h-r-login li.h-r-user span {
  max-width: 150px;
}
</style>
