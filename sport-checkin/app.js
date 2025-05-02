// app.js
App({
  globalData: {
    userInfo: null,
    weeklyScore: 0,
    weeklyTarget: 180, // 每周目标分数
    dailyMinScore: 20, // 单日最低有效分数
    dailyMaxScore: 70, // 单日最高有效分数
    sportTypes: [
      { id: 1, name: '跑步', unit: '公里', scoreRate: 15, unitLabel: '公里' },
      { id: 2, name: '跳绳', unit: '分钟', scoreRate: 2, unitLabel: '分钟' },
      { id: 3, name: 'keep运动', unit: '分钟', scoreRate: 2, unitLabel: '分钟' },
      { id: 4, name: '羽毛球', unit: '小时', scoreRate: 70, unitLabel: '小时' },
      { id: 5, name: '游泳', unit: '千米', scoreRate: 70, unitLabel: '千米' },
      { id: 6, name: '徒步', unit: '步数', scoreRate: 0.0025, unitLabel: '步' },
      { id: 7, name: '轻量运动', unit: '分钟', scoreRate: 1, unitLabel: '分钟' },
      { id: 8, name: '重量运动', unit: '分钟', scoreRate: 1.5, unitLabel: '分钟' }
    ],
    checkinRecords: [],
    specialRequests: [] // 特殊情况申请记录
  },
  
  onLaunch: function() {
    // 获取本地存储的用户数据
    const userInfo = wx.getStorageSync('userInfo');
    const checkinRecords = wx.getStorageSync('checkinRecords') || [];
    const specialRequests = wx.getStorageSync('specialRequests') || [];
    
    if (userInfo) {
      this.globalData.userInfo = userInfo;
    }
    
    if (checkinRecords.length > 0) {
      this.globalData.checkinRecords = checkinRecords;
      // 计算本周累计分数
      this.calculateWeeklyScore();
    }
    
    if (specialRequests.length > 0) {
      this.globalData.specialRequests = specialRequests;
    }
    
    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
      }
    });
  },
  
  // 计算本周累计分数
  calculateWeeklyScore: function() {
    const now = new Date();
    const dayOfWeek = now.getDay() || 7; // 将周日的0转为7
    const mondayTime = new Date(now.getFullYear(), now.getMonth(), now.getDate() - dayOfWeek + 1).getTime();
    
    let weeklyScore = 0;
    this.globalData.checkinRecords.forEach(record => {
      const recordDate = new Date(record.date).getTime();
      if (recordDate >= mondayTime) {
        weeklyScore += record.score;
      }
    });
    
    this.globalData.weeklyScore = weeklyScore;
    return weeklyScore;
  },
  
  // 计算运动分数
  calculateScore: function(sportType, amount) {
    const sport = this.globalData.sportTypes.find(item => item.id === sportType);
    if (!sport) return 0;
    
    let score = amount * sport.scoreRate;
    
    // 单日不足20分记为0分，单日上限70分
    if (score < this.globalData.dailyMinScore) {
      score = 0;
    } else if (score > this.globalData.dailyMaxScore) {
      score = this.globalData.dailyMaxScore;
    }
    
    return Math.floor(score);
  },
  
  // 添加打卡记录
  addCheckinRecord: function(record) {
    // 检查是否已经打卡
    const today = new Date().toISOString().split('T')[0];
    const existingRecord = this.globalData.checkinRecords.find(r => r.date === today);
    
    if (existingRecord) {
      // 更新今日记录
      existingRecord.sportType = record.sportType;
      existingRecord.amount = record.amount;
      existingRecord.score = record.score;
      existingRecord.image = record.image;
      existingRecord.remark = record.remark;
    } else {
      // 添加新记录
      this.globalData.checkinRecords.push({
        ...record,
        date: today
      });
    }
    
    // 更新本周累计分数
    this.calculateWeeklyScore();
    
    // 保存到本地存储
    wx.setStorageSync('checkinRecords', this.globalData.checkinRecords);
    
    return true;
  },
  
  // 申请特殊情况（如生理期）
  applySpecialRequest: function(request) {
    this.globalData.specialRequests.push(request);
    wx.setStorageSync('specialRequests', this.globalData.specialRequests);
    return true;
  },
  
  // 检查是否需要发红包
  checkPenalty: function() {
    const weeklyScore = this.calculateWeeklyScore();
    const target = this.globalData.weeklyTarget;
    
    if (weeklyScore < target) {
      return {
        needPenalty: true,
        amount: target - weeklyScore // 差额发红包（1分=1元钱）
      };
    }
    
    return {
      needPenalty: false,
      amount: 0
    };
  }
});