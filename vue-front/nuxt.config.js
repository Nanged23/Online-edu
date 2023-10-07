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
      target: "http://zxxzfront.natapp1.cc", // 目标接口域名
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
    title: "{{ name }}",
    meta: [
      { charset: "utf-8" },
      { name: "viewport", content: "width=device-width, initial-scale=1" },
      {
        hid: "description",
        name: "description",
        content: "{{escape description }}"
      }
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
