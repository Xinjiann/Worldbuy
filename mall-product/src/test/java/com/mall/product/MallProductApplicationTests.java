package com.mall.product;

import com.mall.product.entity.BrandEntity;
import com.mall.product.service.BrandService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
@RunWith(SpringRunner.class)
@SpringBootTest
class MallProductApplicationTests {

  @Autowired
  BrandService brandService;
  @Test
  void contextLoads() {
    BrandEntity brandEntity = new BrandEntity();
    brandEntity.setDescript("");
    brandEntity.setName("华为");
    brandService.save(brandEntity);
  }

}
