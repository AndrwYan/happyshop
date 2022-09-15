package com.imooc.ecommerce.common;

import lombok.Data;

import java.util.List;

/**
 * @Description: 封装用于返回对象列表
 * @Author: yfk
 **/
@Data
public class BasePageResponse<T> {

        /**
         * 总页数
         */
        private long totalPage;

        /**
         * 当前页数
         */
        private long currentPage;

        /**
         * 总数
         */
        private long totalNum;

        /**
         * 内容
         */
        private List<T> contentList;

}
