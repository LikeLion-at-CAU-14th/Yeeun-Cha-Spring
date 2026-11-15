package com.example.likelion14th_springboot.controller;

import com.example.likelion14th_springboot.dto.response.ProductResponseDto;
import com.example.likelion14th_springboot.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Test
    @DisplayName("전체 상품 목록을 조회하면 200 OK와 상품 목록을 반환한다.")
    void getAllProducts_Success() throws Exception {
        // Given
        ProductResponseDto product1 =
                ProductResponseDto.builder()
                        .id(1L)
                        .name("멋사 티셔츠")
                        .price(15000)
                        .stock(10)
                        .description("14기 굿즈")
                        .build();

        ProductResponseDto product2 =
                ProductResponseDto.builder()
                        .id(2L)
                        .name("멋사 키링")
                        .price(5000)
                        .stock(20)
                        .description("키링")
                        .build();

        given(productService.getAllProducts())
                .willReturn(List.of(product1, product2));

        // When & Then
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name")
                        .value("멋사 티셔츠"))
                .andExpect(jsonPath("$[0].price")
                        .value(15000))
                .andExpect(jsonPath("$[1].name")
                        .value("멋사 키링"))
                .andExpect(jsonPath("$[1].stock")
                        .value(20));

        verify(productService).getAllProducts();
    }
}