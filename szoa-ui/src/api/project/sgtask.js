import request from '@/utils/request'

// 查询任务列表
export function listSgtask(query) {
  return request({
    url: '/project/sgtask/list',
    method: 'get',
    params: query
  })
}

// 查询任务详细
export function getSgtask(taskId) {
  return request({
    url: '/project/sgtask/' + taskId,
    method: 'get'
  })
}

// 新增任务
export function addSgtask(data) {
  return request({
    url: '/project/sgtask',
    method: 'post',
    data: data
  })
}

// 修改任务
export function updateSgtask(data) {
  return request({
    url: '/project/sgtask',
    method: 'put',
    data: data
  })
}

// 删除任务
export function delSgtask(taskId) {
  return request({
    url: '/project/sgtask/' + taskId,
    method: 'delete'
  })
}

// 存储附件路径
export function addTaskFiles(data) {
  return request({
    url: '/project/sgtask/addTaskFiles',
    method: 'post',
    data:data
  })
}

// 删除附件路径
export function deletTaskFiles(data) {
  return request({
    url: '/project/sgtask/deletTaskFiles',
    method: 'post',
    data:data
  })
}

// 获取附件路径
export function getTaskFiles(taskId) {
  return request({
    url: '/project/sgtask/getTaskFiles/' + taskId,
    method: 'get'
  })
}
