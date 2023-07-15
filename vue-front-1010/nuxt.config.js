module.exports = {
  /*
   ** Headers of the page
   */
  modules: ["@nuxtjs/axios", "@nuxtjs/proxy"],
  axios: {
    proxy: true, // 表示开启代理
    prefix: "/api", // 表示给请求url加个前缀 /api
    credentials: true // 表示跨域请求时是否需要使用凭证
  },
  proxy: {
    "/api": {
      target: "http://localhost:8160", // 目标接口域名
      changeOrigin: true, // 表示是否跨域
      pathRewrite: {
        "^/api": "/" // 把 /api 替换成 /
      }
    }
  },
  build: {
    vendor: ["axios"] //为防止重复打包
  },
  head: {
    title: "e学网- Java视频|HTML5视频|前端视频|Python视频|大数据视频",
    meta: [
      { charset: "utf-8" },
      { name: "viewport", content: "width=device-width, initial-scale=1" },
      {   hid: 'keywords', 
          name: 'keywords', 
          content: 'e学网,IT在线视频教程,Java视频,HTML5视频,前端视频,Python视频,大数据视频' },
      {     hid: 'description', 
            name: 'description', 
            content: 'e学网是国内领先的IT在线视频学习平台、职业教育平台。截止目前,e学网线上、线下学习人次数以万计！会同上百个知名开发团队联合制定的Java、HTML5前端、大数据、Python等视频课程，被广大学习者及IT工程师誉为：业界最适合自学、代码量最大、案例最多、实战性最强、技术最前沿的IT系列视频课程！' },
    ],
    link: [{ rel: "icon", type: "image/x-icon", href: "/favicon.ico" }]
  },
  /*
   ** Customize the progress bar color
   */
  loading: { color: "#3B8070" },
  /*
   ** Build configuration
   */
  build: {
    /*
     ** Run ESLint on save
     */
    extend(config, { isDev, isClient }) {
      if (isDev && isClient) {
        config.module.rules.push({
          enforce: "pre",
          test: /\.(js|vue)$/,
          loader: "eslint-loader",
          exclude: /(node_modules)/
        });
      }
    },
    transpile: [({ filePath }) => /(\.esm\.js|\.mjs)$/.test(filePath)]
  },
  plugins: [
    {
      src: "~/plugins/nuxt-swiper-plugin.js",
      ssr: false
    },
    "@/plugins/axios.js"
  ],
  css: ["swiper/dist/css/swiper.css"]
};
