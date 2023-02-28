import request from '@/utils/request'

// 查询周报填写列表
export function listReport(query) {
  return request({
    url: '/project/report/list',
    method: 'get',
    params: query
  })
}

// 查询周报填写详细
export function getReport(weekrId) {
  return request({
    url: '/project/report/' + weekrId,
    method: 'get'
  })
}

// 新增周报填写
export function addReport(data) {
  return request({
    url: '/project/report',
    method: 'post',
    data: data
  })
}

// 修改周报填写
export function updateReport(data) {
  return request({
    url: '/project/report',
    method: 'put',
    data: data
  })
}

// 删除周报填写
export function delReport(weekrId) {
  return request({
    url: '/project/report/' + weekrId,
    method: 'delete'
  })
}
