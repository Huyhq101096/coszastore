package com.cybersoft.cozastore.controller;

import com.cybersoft.cozastore.exception.FileNotFoundException;
import com.cybersoft.cozastore.payload.request.ProductRequest;
import com.cybersoft.cozastore.payload.response.BaseResponse;
import com.cybersoft.cozastore.service.impl.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Value("${root.file.path}")
    private String rootPath;

    @Autowired
    private IProductService iProductService;

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping("/{id}")
    public ResponseEntity<?> getDetailProduct(@PathVariable int id) {
        BaseResponse response = new BaseResponse();
        response.setData(iProductService.getDetailProduct(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @GetMapping("/getAllCategory/{id}")
    public ResponseEntity<?> getProductByCategoryId(
            HttpServletRequest request,
            @PathVariable("id") int id) {

        logger.trace("Hello trace logger");
        logger.debug("Hello debug logger");
        logger.info("Hello info logger");
        logger.warn("Hello warn logger");
        logger.trace("Hello error logger");


        String hostName = request.getHeader("host");
//        System.out.println(request.getHeader("host"));
//        System.out.println(request.getProtocol());
//        System.out.println(request.getContextPath());
        BaseResponse response = new BaseResponse();
        response.setData(iProductService.getProductByCategoryId(id,hostName));
        return new ResponseEntity<>(response, HttpStatus.OK);
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
     * */

    @GetMapping("/file/{filename}")
    public ResponseEntity<?> dowloadFileProduct(@PathVariable String filename) {
        try {
            // Định nghĩa đường dẫn folder lưu file
            Path path = Paths.get(rootPath);
            // Định nghĩa đường dẫn tới file được lưu.
            Path pathFile = path.resolve(filename);
            Resource resource = new UrlResource(pathFile.toUri());
            if(resource.exists() || resource.isReadable()) {
                // Cho phép download file

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + filename + "\"")
                        .body(resource);
            } else {
                throw new FileNotFoundException("Không tìm thấy file");
            }
        } catch (Exception e) {
                // Lỗi
            throw new FileNotFoundException("Không thể tìm thấy file");
        }

    }

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
            String rootFolder = rootPath;
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
