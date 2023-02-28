import request from '@/utils/request'

// 查询日报列表
export function listDayreport(query) {
  return request({
    url: '/project/dayreport/list',
    method: 'get',
    params: query
  })
}

// 查询日报详细
export function getDayreport(dayrId) {
  return request({
    url: '/project/dayreport/' + dayrId,
    method: 'get'
  })
}

// 新增日报
export function addDayreport(data) {
  return request({
    url: '/project/dayreport',
    method: 'post',
    data: data
  })
}

// 新增日报
export function addDayHour(data) {
  return request({
    url: '/project/dayreport/addDayHour',
    method: 'post',
    data: data
  })
}

// 修改日报
export function updateDayreport(data) {
  return request({
    url: '/project/dayreport',
    method: 'put',
    data: data
  })
}

// 删除日报
export function delDayreport(dayrId) {
  return request({
    url: '/project/dayreport/' + dayrId,
    method: 'delete'
  })
}
