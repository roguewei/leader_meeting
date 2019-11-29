module.exports = {
  configureWebpack: {
    resolve: {
      alias: {
        // 配置别名，在引用的时候就不需要加../等相对路径
        assets: '@/assets',
        common: '@/common',
        components: '@/components',
        network: '@/network',
        views: '@/views'
      }
    }
  }
}
