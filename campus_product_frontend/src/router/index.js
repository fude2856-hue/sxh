import { createRouter, createWebHistory } from 'vue-router';
import Login from '@/pages/user/login.vue';
import Register from '@/pages/user/register.vue';
import Home from "@/pages/home/home.vue";
import AdminHome from "@/pages/admin/admin_home.vue";
import Information from "@/pages/user/information.vue";
import CreateProduct from "@/pages//product/createproduct.vue";
import Productlist from "@/pages/product/productlist.vue";
import ProductDetail from "@/pages/product/productdetail.vue";
import Feedback from "@/pages/admin/feedback.vue";
import MyMessage from "@/pages/message/mymessage.vue";
import Evaluation from "@/pages/evaluation/myevaluation.vue";
import AdminUser from "@/pages/admin/UserManagement.vue";
import adminproduct from "@/pages/admin/productmanagement.vue";
import adminevaluation from "@/pages/admin/EvaluationManagement.vue";
import MyProduct from "@/pages/myproduct/myproduct.vue";
import Orders from "@/pages/order/orders.vue";
import Interaction from "@/pages/interaction/interaction.vue";
import Setting from "@/pages/home/UserSetting.vue";
import Log from "@/pages/admin/Announcement.vue";
import Settings from "@/pages/admin/AdminSetting.vue";
import Security from "@/pages/admin/SecurityCenter.vue";

const routes = [
    { path: '/login', component: Login },
    { path: '/register', component: Register },
    { path: '/home', component: Home,
    children:[
        {
            path:'information',
            component: Information
        },
        {
            path:'createproduct',
            component: CreateProduct
        },
        {
            path:'productlist',
            component: Productlist
        },
        {
            path:'message',
            component: MyMessage
        },
        {
            path:'evaluation',
            component: Evaluation
        },
        {
            path:'myproduct',
            component: MyProduct
        },
        {
            path:'orders',
            component: Orders
        },
        {
            path:'interaction',
            component: Interaction
        },
        {
            path:'setting',
            component: Setting
        }

    ]
    },
    { path: '/adminhome',component:AdminHome,
    children:[
        {
            path:'feedback',
            component: Feedback
        },
        {
            path:'users',
            component: AdminUser
        },
        {
            path:'products',
            component: adminproduct
        },
        {
            path:'evaluations',
            component: adminevaluation
        },
        {
            path:'logs',
            component: Log
        },
        {
            path:'settings',
            component: Settings
        },
        {
            path:'security',
            component: Security
        }
    ]},
    {path: '/product/:id', component: ProductDetail},
    // 重定向到登录页面，防止用户直接访问其他页面
    { path: '/', redirect: '/login' }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;