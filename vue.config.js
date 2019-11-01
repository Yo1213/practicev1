module.exports = {
  configureWebpack: {
    resolve: {
      // 路径
      // 后缀名，默认已经配置了
      // extentions: [],
      alias: {
        'assets': '@/assets',
        'common': '@/common',
        'components': '@/components',
        'network': '@/network',
        'assets': '@/assets',
        'views': '@/views'
      }
    }
  }
}