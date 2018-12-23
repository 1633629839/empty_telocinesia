package com.baizhi.dto;

import com.baizhi.entity.Banner;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BannerDto implements Serializable {
    private Integer total;
    private List<Banner> rows;
}
