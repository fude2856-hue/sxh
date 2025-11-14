import axios from "axios";
import {ElMessage} from "element-plus";

//创建axios实例,并设置请求超时时间
const request =axios.create({
    //baseURL:"http://localhost:8080",
    timeout:5000
});

request.interceptors.response.use(
    res=>res.data,
    err=>{
        ElMessage.error("请求失败");
        return Promise.reject(err);
    }
);
export default request;