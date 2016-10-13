# CMake 最低版本号要求
cmake_minimum_required(VERSION 3.4.1)

# 项目信息
project(AndroidJni)

# 查找所有源文件并将名称保存到 DIR_SRCS 变量
aux_source_directory(src/main/cpp/ DIR_SRCS)

# 生成动态链接库native-lib
add_library(native-lib SHARED ${DIR_SRCS})

# 查找NDK的log library并保存到变量 LOG-LIB
find_library(LOG-LIB log)

# 添加链接库
target_link_libraries(native-lib ${LOG-LIB})

