package com.xiaofeng.springbootsolr.base;

/**
 * @Auther: 晓枫
 * @Date: 2019/5/3 17:53
 * @Description:
 */
public class PageInfo {

    // 当前页
    private Integer current;
    // 每页大小
    private Integer size;
    // 从第几条开始查询
    private Integer recordStart;

    public Integer getCurrent() {
        return current;
    }
    public void setCurrent(Integer current) {
        this.current = current;
    }
    public Integer getSize() {
        return size;
    }
    public void setSize(Integer size) {
        this.size = size;
    }
    public Integer getRecordStart() {
        if(recordStart == null){
            if (getCurrent() == 0 || getCurrent() == 1) {
                return 0;
            }
            int start = (getCurrent() - 1)* this.size;
            return start < 0 ? 0 : start;
        }else{
            return recordStart;
        }
    }
    public void setRecordStart(Integer recordStart) {
        this.recordStart = recordStart;
    }
}
