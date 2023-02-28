import request from '@/utils/request'

// 查询项目集列表
export function listProset(query) {
  return request({
    url: '/project/proset/list',
    method: 'get',
    params: query
  })
}

// 查询成员拥有项目
export function getUserProject(data) {
  return request({
    url: '/project/proset/getUserProject?userId=' + data,
    method: 'get'
  })
}

// 查询项目集详细
export function getProset(proId) {
  return request({
    url: '/project/proset/' + proId,
    method: 'get'
  })
}

// 查询项目成员信息
export function getProPerson(proId) {
  return request({
    url: '/project/proset/properson/' + proId,
    method: 'get'
  })
}

// 新增项目集
export function addProset(data) {
  return request({
    url: '/project/proset',
    method: 'post',
    data: data
  })
}

// 修改项目集
export function updateProset(data) {
  return request({
    url: '/project/proset',
    method: 'put',
    data: data
  })
}

// 删除项目集
export function delProset(proId) {
  return request({
    url: '/project/proset/' + proId,
    method: 'delete'
  })
}

// 存储附件路径
export function addProFiles(data) {
  return request({
    url: '/project/proset/addProFiles',
    method: 'post',
    data:data
  })
}

// 删除附件路径
export function deletProFiles(data) {
  return request({
    url: '/project/proset/deletProFiles',
    method: 'post',
    data:data
  })
}

// 获取附件路径
export function getProjectFile(proId) {
  return request({
    url: '/project/proset/getProjectFile/' + proId,
    method: 'get'
  })
}

// 获取附件路径
export function selectProHoursCount(proId) {
  return request({
    url: '/project/proset/selectProHoursCount/' + proId,
    method: 'get'
  })
}

