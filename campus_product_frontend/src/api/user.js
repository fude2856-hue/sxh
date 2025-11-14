import request from "@/utils/request";

export const loginUser = data =>request.post("/api/user/login",data);
export const registerUser = data =>request.post("/api/user/register",data);
export const getUserInfo =()=> request.get('/api/user/information');
export const updateUserInfo = data => request.post('/api/user/update', data);


