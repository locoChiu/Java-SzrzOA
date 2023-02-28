import request from '@/utils/request'

// 查询任务包列表
export function listTasks(query) {
  return request({
    url: '/project/tasks/list',
    method: 'get',
    params: query
  })
}

// 查询任务包详细
export function getTasks(tasksId) {
  return request({
    url: '/project/tasks/' + tasksId,
    method: 'get'
  })
}


// 新增任务包
export function addTasks(data) {
  return request({
    url: '/project/tasks',
    method: 'post',
    data: data
  })
}


// 指派任务包
export function assignTasks(data) {
  return request({
    url: '/project/tasks/assignTasks',
    method: 'post',
    data: data
  })
}


// 修改任务包
export function updateTasks(data) {
  return request({
    url: '/project/tasks',
    method: 'put',
    data: data
  })
}

// 删除任务包
export function delTasks(tasksId) {
  return request({
    url: '/project/tasks/' + tasksId,
    method: 'delete'
  })
}

// 删除任务包
export function getAllUserTask(tasksId) {
  return request({
    url: '/project/tasks/getUserAllList/' + tasksId,
    method: 'get'
  })
}
