// statistics.js
Page({
  data: {
    weeklyScore: 0,
    weeklyTarget: 180,
    weekProgress: 0,
    penalty: {
      needPenalty: false,
      amount: 0
    },
    weeklyRecords: [],
    currentWeek: '',
    specialRequests: []
  },
  
  onLoad: function() {
    this.loadData();
  },
  
  onShow: function() {
    this.loadData();
  },
  
  // 加载数据
  loadData: function() {
    const app = getApp();
    
    // 获取本周日期范围
    const weekRange = this.getWeekRange();
    const currentWeek = `${this.formatDate(weekRange.start)} - ${this.formatDate(weekRange.end)}`;
    
    // 计算本周得分和进度
    const weeklyScore = app.calculateWeeklyScore();
    const weeklyTarget = app.globalData.weeklyTarget;
    const weekProgress = Math.min(100, (weeklyScore / weeklyTarget) * 100);
    
    // 获取本周打卡记录
    const weeklyRecords = this.getWeeklyRecords(weekRange.start, weekRange.end);
    
    // 计算是否需要发红包
    const penalty = app.checkPenalty();
    
    // 获取特殊申请记录
    const specialRequests = app.globalData.specialRequests;
    
    this.setData({
      weeklyScore,
      weeklyTarget,
      weekProgress,
      penalty,
      weeklyRecords,
      currentWeek,
      specialRequests
    });
  },
  
  // 获取本周日期范围
  getWeekRange: function() {
    const now = new Date();
    const dayOfWeek = now.getDay() || 7; // 将周日的0转为7
    
    // 计算本周一的日期
    const monday = new Date(now.getFullYear(), now.getMonth(), now.getDate() - dayOfWeek + 1);
    
    // 计算本周日的日期
    const sunday = new Date(now.getFullYear(), now.getMonth(), now.getDate() - dayOfWeek + 7);
    
    return {
      start: monday,
      end: sunday
    };
  },
  
  // 获取本周打卡记录
  getWeeklyRecords: function(startDate, endDate) {
    const app = getApp();
    const startTime = startDate.getTime();
    const endTime = endDate.getTime();
    
    // 筛选本周的打卡记录
    const weeklyRecords = app.globalData.checkinRecords.filter(record => {
      const recordDate = new Date(record.date).getTime();
      return recordDate >= startTime && recordDate <= endTime;
    });
    
    // 按日期排序
    weeklyRecords.sort((a, b) => new Date(a.date) - new Date(b.date));
    
    // 格式化日期显示
    return weeklyRecords.map(record => {
      const date = new Date(record.date);
      const weekDay = ['周日', '周一', '周二', '周三', '周四', '周五', '周六'][date.getDay()];
      return {
        ...record,
        displayDate: `${date.getMonth() + 1}月${date.getDate()}日 ${weekDay}`
      };
    });
  },
  
  // 格式化日期
  formatDate: function(date) {
    return `${date.getMonth() + 1}月${date.getDate()}日`;
  },
  
  // 生成红包
  generateRedPacket: function() {
    if (!this.data.penalty.needPenalty) {
      wx.showToast({
        title: '无需发红包',
        icon: 'none'
      });
      return;
    }
    
    // 这里应该调用微信支付API生成红包
    // 由于无法直接调用微信支付API，这里只做模拟
    wx.showModal({
      title: '红包提示',
      content: `本周运动未达标，需发红包${this.data.penalty.amount}元`,
      confirmText: '去支付',
      success: (res) => {
        if (res.confirm) {
          wx.showToast({
            title: '支付功能暂未开放',
            icon: 'none'
          });
        }
      }
    });
  },
  
  // 查看特殊申请详情
  viewSpecialRequest: function(e) {
    const index = e.currentTarget.dataset.index;
    const request = this.data.specialRequests[index];
    
    wx.showModal({
      title: '特殊申请详情',
      content: `申请日期：${request.date}\n申请原因：${request.reason}\n申请天数：${request.days}天\n状态：${this.getStatusText(request.status)}`,
      showCancel: false
    });
  },
  
  // 获取状态文本
  getStatusText: function(status) {
    const statusMap = {
      'pending': '待审核',
      'approved': '已通过',
      'rejected': '已拒绝'
    };
    return statusMap[status] || '未知';
  }
});