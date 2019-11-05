import {request} from './request'
export function getDetail(iid) {
  return request({
    url: '/api/v1/detail',
    params: {
      iid
    }
  })
}
export function getRecommend() {
  return request({
    url: '/api/v1/recommend'
  })
}
export class Goods {
  constructor(itemInfo, columns, service) {
    this.desc = itemInfo.title;
    this.newPrice = itemInfo.price;
    this.oldPrice = itemInfo.oldPrice;
    this.discount = itemInfo.discountDesc;
    this.columns = columns;
    this.services = service;
    this.discountBgColor = itemInfo.discountBgColor;
  }
}