package com.cybersoft.cozastore.controller;

import com.cybersoft.cozastore.payload.request.ProductRequest;
import com.cybersoft.cozastore.payload.response.BaseResponse;
import com.cybersoft.cozastore.service.impl.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService iProductService;


    @GetMapping("/{id}")
    public ResponseEntity<?> getProductByCategoryId(@PathVariable("id") int id) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(iProductService.getProductByCategoryId(id));
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    /**
     *
     * có 2 cách upload file chính
     * Cách 1: chuyển file về dạng base64
     *       - Từ file chuyển thành chuỗi -> đẩy chuỗi lên server -> từ chuỗi chuyển lại thành file.
     *       - Nhược điểm : nhân khoảng 1.5 kích thước file.
     *       - Ưu điểm : Vì file đã chuyển thành chuỗi nên lưu trữ được dưới dạng chuỗi.
     * cách 2: sử dụng Multipartfile
     *       - Mở một luồng đọc vào file ( stream )
     *       - C://folder/tenhinh.png
     *
     *
     * */

    @PostMapping("")
    public ResponseEntity<?> addProduct(@Valid ProductRequest productRequest) {

        // Lấy tên file và đuôi file
//        String filename = file.getOriginalFilename();
        // Đường dẫn lưu trữ file
//        String rootFolder = "E:\\Git\\images\\image";
//        Path pathRoot = Paths.get(rootFolder);
        // Nếu đường dẫn ko tồn tại thì tạo folder
//        if(!Files.exists(pathRoot)) {
//            Files.createDirectory(pathRoot);
//        }
        // resolve <=> /
        // pathRoot.resolve(filename) <=> E:\Git\images\image\file.png
//        Files.copy(file.getInputStream(),pathRoot.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
        String filename = productRequest.getFile().getOriginalFilename();

        try {
            String rootFolder = "E:\\Git\\images\\image";
            Path pathRoot = Paths.get(rootFolder);
            if(!Files.exists(pathRoot)) {
                Files.createDirectory(pathRoot);
            }
            Files.copy(productRequest.getFile().getInputStream(),pathRoot.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
            iProductService.addProduct(productRequest);
        } catch (Exception e) {

        }


        return new ResponseEntity<>(filename,HttpStatus.OK);
    }
}
