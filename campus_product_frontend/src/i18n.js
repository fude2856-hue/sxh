import { createI18n } from 'vue-i18n'

const messages = {
    zh: {
        // 管理后台 & 用户端
        adminTitle: '后台管理系统',
        userTitle: '校园二手交易平台',
        announcement: '系统公告',
        userManage: '用户管理',
        productManage: '商品管理',
        commentManage: '评论管理',
        feedback: '意见反馈',
        setting: '系统设置',
        security: '安全中心',

        products: '商品',
        createProduct: '发布商品',
        myProducts: '我的商品',
        myOrders: '我的订单',
        myFavorites: '我的收藏',
        myComments: '我的评论',
        myMessages: '我的消息',
        profile: '个人信息',

        // 系统公告模块
        userId: '用户ID',
        operationDetail: '操作内容',
        time: '时间',

        // 系统设置模块
        themeMode: '主题模式',
        dark: '暗色',
        light: '亮色',
        sidebarColor: '侧边栏颜色',
        languageSelection: '语言选择',
        selectLanguage: '选择语言',
        simplifiedChinese: '简体中文',
        english: 'English',
        languageChanged: '语言已切换为：{lang}',

        // 安全中心模块
        accountOperations: '账号操作',
        logout: '退出登录',
        switchAccount: '切换账号',
        changePassword: '修改密码',
        oldPassword: '原密码',
        newPassword: '新密码',
        confirmChange: '确认修改',
        enterOldPassword: '请输入原密码',
        enterNewPassword: '请输入新密码',
        passwordChangeSuccess: '密码修改成功，请重新登录',
        passwordChangeFail: '密码修改失败，请重试',

        // 用户管理模块（新增）
        AdminUserManagement: '新增管理员',
        username: '用户名',
        email: '邮箱',
        password: '密码',
        addAdminUser: '添加管理员',
        reset: '重置',
        getUsersFailed: '获取用户列表失败',
        addUserFailed: '添加用户失败',

        // 输入提示（防止缺失）
        enterUsername: '请输入用户名',
        enterEmail: '请输入邮箱',
        enterPassword: '请输入密码',

        // ✅ 中文 zh 添加项
        deleteAccount: '注销账号',
        accountDeleted: '账号已注销',
        deleteAccountFailed: '注销失败，请稍后重试',
        confirmDeleteTitle: '确认注销',
        confirmDeleteContent: '注销账号将永久删除数据，是否继续？',
        confirm: '确认',
        cancel: '取消',
    },
    en: {
        // 管理后台 & 用户端
        adminTitle: 'Admin Dashboard',
        userTitle: 'Campus Secondhand Market',
        announcement: 'Announcements',
        userManage: 'User Management',
        productManage: 'Product Management',
        commentManage: 'Comment Management',
        feedback: 'Feedback',
        setting: 'Settings',
        security: 'Security',

        products: 'Products',
        createProduct: 'Post Product',
        myProducts: 'My Products',
        myOrders: 'My Orders',
        myFavorites: 'My Favorites',
        myComments: 'My Comments',
        myMessages: 'My Messages',
        profile: 'Profile',

        // 系统公告模块
        userId: 'User ID',
        operationDetail: 'Operation Detail',
        time: 'Time',

        // 系统设置模块
        themeMode: 'Theme Mode',
        dark: 'Dark',
        light: 'Light',
        sidebarColor: 'Sidebar Color',
        languageSelection: 'Language Selection',
        selectLanguage: 'Select Language',
        simplifiedChinese: 'Simplified Chinese',
        english: 'English',
        languageChanged: 'Language switched to: {lang}',

        // 安全中心模块
        accountOperations: 'Account Actions',
        logout: 'Logout',
        switchAccount: 'Switch Account',
        changePassword: 'Change Password',
        oldPassword: 'Old Password',
        newPassword: 'New Password',
        confirmChange: 'Confirm Change',
        enterOldPassword: 'Please enter old password',
        enterNewPassword: 'Please enter new password',
        passwordChangeSuccess: 'Password changed successfully, please login again',
        passwordChangeFail: 'Password change failed, please try again',

        // 用户管理模块（新增）
        AdminUserManagement: 'AdminUser Management',
        username: 'Username',
        email: 'Email',
        password: 'Password',
        addAdminUser: 'Add AdminUser',
        reset: 'Reset',
        getUsersFailed: 'Failed to get users',
        addUserFailed: 'Failed to add user',

        // 输入提示（防止缺失）
        enterUsername: 'Please enter username',
        enterEmail: 'Please enter email',
        enterPassword: 'Please enter password',

        // ✅ 英文 en 添加项
        deleteAccount: 'Delete Account',
        accountDeleted: 'Account deleted',
        deleteAccountFailed: 'Failed to delete account. Please try again later.',
        confirmDeleteTitle: 'Confirm Deletion',
        confirmDeleteContent: 'Deleting your account is irreversible. Continue?',
        confirm: 'Confirm',
        cancel: 'Cancel',
    }
}

const i18n = createI18n({
    legacy: false,
    locale: localStorage.getItem('language') || 'zh',
    messages,
})

export default i18n
