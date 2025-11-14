import request from "@/utils/request";
// 获取所有用户
export const getAllUsers = () => request.get("/api/admin/user/findAll");

// 根据 ID 查询用户
export const getUserById = (id) => request.get("/api/admin/user/findById", { params: { id } });

// 根据手机号查询用户
export const getUserByPhone = (phone) => request.get("/api/admin/user/findByPhone", { params: { phone } });

// 根据用户名查询用户
export const getUserByUsername = (username) => request.get("/api/admin/user/findByUsername", { params: { username } });

// 删除用户（根据id）
export const deleteUserById = (id) => request.delete("/api/admin/user/deleteById", { params: { id } });

// //  新增：删除用户（根据用户名）
// export const deleteUserByUsername = (username) => request.delete("/api/admin/user/deleteByUsername", { params: { username } });
//
// //  新增：删除用户（根据手机号）
// export const deleteUserByPhone = (phone) => request.delete("/api/admin/user/deleteByPhone", { params: { phone } });