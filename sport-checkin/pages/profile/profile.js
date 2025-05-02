// profile.js
Page({
  data: {
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    totalScore: 0,
    totalCheckins: 0,
    specialRequests: [],
    showAbout: false
  },
  
  onLoad: function() {
    const app = getApp();
    
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
    
    this.loadUserData();
  },
  
  onShow: function() {
    this.loadUserData();
  },
  
  // 加载用户数据
  loadUserData: function() {
    const app = getApp();
    const checkinRecords = app.globalData.checkinRecords;
    const specialRequests = app.globalData.specialRequests;
    
    // 计算总分数
    let totalScore = 0;
    checkinRecords.forEach(record => {
      totalScore += record.score;
    });
    
    this.setData({
      totalScore,
      totalCheckins: checkinRecords.length,
      specialRequests
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
  
  // 清除数据
  clearData: function() {
    wx.showModal({
      title: '确认清除',
      content: '确定要清除所有打卡记录吗？此操作不可恢复！',
      success: (res) => {
        if (res.confirm) {
          const app = getApp();
          app.globalData.checkinRecords = [];
          app.globalData.weeklyScore = 0;
          wx.setStorageSync('checkinRecords', []);
          
          this.loadUserData();
          
          wx.showToast({
            title: '数据已清除',
            icon: 'success'
          });
        }
      }
    });
  },
  
  // 显示关于信息
  showAboutInfo: function() {
    this.setData({
      showAbout: true
    });
  },
  
  // 隐藏关于信息
  hideAboutInfo: function() {
    this.setData({
      showAbout: false
    });
  },
  
  // 联系我们
  contactUs: function() {
    wx.showModal({
      title: '联系我们',
      content: '如有问题或建议，请联系群主。',
      showCancel: false
    });
  },
  
  // 退出登录
  logout: function() {
    wx.showModal({
      title: '确认退出',
      content: '确定要退出登录吗？',
      success: (res) => {
        if (res.confirm) {
          const app = getApp();
          app.globalData.userInfo = null;
          wx.removeStorageSync('userInfo');
          
          this.setData({
            userInfo: {},
            hasUserInfo: false
          });
          
          wx.showToast({
            title: '已退出登录',
            icon: 'success'
          });
        }
      }
    });
  }
});