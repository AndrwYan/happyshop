package com.imooc.ecommerce.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description: 封装用于返回对象列表
 * @Author: yanfk
 **/
@Data
public class BasePageResponse<T> {

        private static final long serialVersionUID=1L;

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
