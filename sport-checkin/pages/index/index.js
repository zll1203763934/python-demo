// index.js
Page({
  data: {
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    weeklyScore: 0,
    weeklyTarget: 180,
    weekProgress: 0,
    todayCheckin: null,
    recentRecords: []
  },
  
  onLoad: function() {
    const app = getApp();
    
    // 设置全局配置
    this.setData({
      weeklyTarget: app.globalData.weeklyTarget
    });
    
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      });
    } else if (this.data.canIUse) {
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        });
      };
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo;
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          });
        }
      });
    }
    
    this.loadData();
  },
  
  onShow: function() {
    this.loadData();
  },
  
  // 加载数据
  loadData: function() {
    const app = getApp();
    const weeklyScore = app.calculateWeeklyScore();
    const weekProgress = Math.min(100, (weeklyScore / this.data.weeklyTarget) * 100);
    
    // 获取今日打卡记录
    const today = new Date().toISOString().split('T')[0];
    const todayCheckin = app.globalData.checkinRecords.find(r => r.date === today);
    
    // 获取最近打卡记录（最多5条）
    const recentRecords = [...app.globalData.checkinRecords]
      .sort((a, b) => new Date(b.date) - new Date(a.date))
      .slice(0, 5);
    
    this.setData({
      weeklyScore,
      weekProgress,
      todayCheckin,
      recentRecords
    });
  },
  
  // 获取用户信息
  getUserInfo: function(e) {
    const app = getApp();
    if (e.detail.userInfo) {
      app.globalData.userInfo = e.detail.userInfo;
      wx.setStorageSync('userInfo', e.detail.userInfo);
      this.setData({
        userInfo: e.detail.userInfo,
        hasUserInfo: true
      });
    }
  },
  
  // 跳转到打卡页面
  goToCheckin: function() {
    wx.switchTab({
      url: '/pages/checkin/checkin'
    });
  },
  
  // 跳转到统计页面
  goToStatistics: function() {
    wx.switchTab({
      url: '/pages/statistics/statistics'
    });
  }
});