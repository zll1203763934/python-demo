// checkin.js
Page({
  data: {
    sportTypes: [],
    selectedSport: null,
    amount: '',
    remark: '',
    tempImagePath: '',
    isSubmitting: false,
    showSpecialRequest: false,
    specialReason: '',
    specialDays: 1,
    todayCheckin: null,
    todayDate: ''
  },
  
  onLoad: function() {
    const app = getApp();
    // 获取运动类型列表
    this.setData({
      sportTypes: app.globalData.sportTypes,
      todayDate: this.formatDate(new Date())
    });
    
    // 检查今日是否已打卡
    this.checkTodayCheckin();
  },
  
  onShow: function() {
    // 每次显示页面时检查今日是否已打卡
    this.checkTodayCheckin();
  },
  
  // 检查今日是否已打卡
  checkTodayCheckin: function() {
    const app = getApp();
    const today = new Date().toISOString().split('T')[0];
    const todayCheckin = app.globalData.checkinRecords.find(r => r.date === today);
    
    if (todayCheckin) {
      // 如果今日已打卡，显示打卡记录
      this.setData({
        todayCheckin,
        selectedSport: null,
        amount: '',
        remark: '',
        tempImagePath: ''
      });
    } else {
      this.setData({
        todayCheckin: null
      });
    }
  },
  
  // 选择运动类型
  selectSport: function(e) {
    const sportId = parseInt(e.currentTarget.dataset.id);
    const selectedSport = this.data.sportTypes.find(item => item.id === sportId);
    
    this.setData({
      selectedSport
    });
  },
  
  // 输入运动量
  inputAmount: function(e) {
    this.setData({
      amount: e.detail.value
    });
  },
  
  // 输入备注
  inputRemark: function(e) {
    this.setData({
      remark: e.detail.value
    });
  },
  
  // 选择图片
  chooseImage: function() {
    wx.chooseImage({
      count: 1,
      sizeType: ['compressed'],
      sourceType: ['album', 'camera'],
      success: (res) => {
        this.setData({
          tempImagePath: res.tempFilePaths[0]
        });
      }
    });
  },
  
  // 预览图片
  previewImage: function() {
    if (this.data.tempImagePath) {
      wx.previewImage({
        urls: [this.data.tempImagePath]
      });
    }
  },
  
  // 删除图片
  deleteImage: function() {
    this.setData({
      tempImagePath: ''
    });
  },
  
  // 提交打卡
  submitCheckin: function() {
    if (!this.data.selectedSport) {
      wx.showToast({
        title: '请选择运动类型',
        icon: 'none'
      });
      return;
    }
    
    if (!this.data.amount || isNaN(parseFloat(this.data.amount))) {
      wx.showToast({
        title: '请输入有效的运动量',
        icon: 'none'
      });
      return;
    }
    
    if (!this.data.tempImagePath) {
      wx.showToast({
        title: '请上传运动截图',
        icon: 'none'
      });
      return;
    }
    
    this.setData({
      isSubmitting: true
    });
    
    const app = getApp();
    const amount = parseFloat(this.data.amount);
    const score = app.calculateScore(this.data.selectedSport.id, amount);
    
    // 上传图片到服务器（这里模拟上传成功）
    setTimeout(() => {
      // 添加打卡记录
      const record = {
        sportType: this.data.selectedSport.id,
        sportName: this.data.selectedSport.name,
        amount: amount,
        unitLabel: this.data.selectedSport.unitLabel,
        score: score,
        image: this.data.tempImagePath, // 实际应用中应该是上传后的图片URL
        remark: this.data.remark
      };
      
      const result = app.addCheckinRecord(record);
      
      if (result) {
        wx.showToast({
          title: '打卡成功',
          icon: 'success'
        });
        
        // 重置表单并刷新页面
        this.setData({
          selectedSport: null,
          amount: '',
          remark: '',
          tempImagePath: '',
          isSubmitting: false
        });
        
        // 检查今日是否已打卡
        this.checkTodayCheckin();
      } else {
        wx.showToast({
          title: '打卡失败，请重试',
          icon: 'none'
        });
        this.setData({
          isSubmitting: false
        });
      }
    }, 1000);
  },
  
  // 显示特殊申请表单
  showSpecialRequestForm: function() {
    this.setData({
      showSpecialRequest: true
    });
  },
  
  // 隐藏特殊申请表单
  hideSpecialRequestForm: function() {
    this.setData({
      showSpecialRequest: false,
      specialReason: '',
      specialDays: 1
    });
  },
  
  // 输入特殊原因
  inputSpecialReason: function(e) {
    this.setData({
      specialReason: e.detail.value
    });
  },
  
  // 设置特殊天数
  setSpecialDays: function(e) {
    this.setData({
      specialDays: parseInt(e.detail.value)
    });
  },
  
  // 提交特殊申请
  submitSpecialRequest: function() {
    if (!this.data.specialReason) {
      wx.showToast({
        title: '请输入申请原因',
        icon: 'none'
      });
      return;
    }
    
    const app = getApp();
    const today = new Date();
    
    const request = {
      date: today.toISOString().split('T')[0],
      reason: this.data.specialReason,
      days: this.data.specialDays,
      status: 'pending' // pending, approved, rejected
    };
    
    const result = app.applySpecialRequest(request);
    
    if (result) {
      wx.showToast({
        title: '申请已提交',
        icon: 'success'
      });
      
      this.setData({
        showSpecialRequest: false,
        specialReason: '',
        specialDays: 1
      });
    } else {
      wx.showToast({
        title: '申请失败，请重试',
        icon: 'none'
      });
    }
  },
  
  // 格式化日期
  formatDate: function(date) {
    const year = date.getFullYear();
    const month = date.getMonth() + 1;
    const day = date.getDate();
    return `${year}年${month}月${day}日`;
  }
});