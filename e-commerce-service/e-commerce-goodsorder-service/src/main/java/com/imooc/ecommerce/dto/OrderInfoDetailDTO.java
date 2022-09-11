package com.imooc.ecommerce.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfoDetailDTO {

    private OrderInfoDTO orderInfoDTO;

    private List<OrderItemDTO> orderItemDTO;

}
