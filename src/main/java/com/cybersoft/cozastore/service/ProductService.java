package com.cybersoft.cozastore.service;

import com.cybersoft.cozastore.entity.CategoryEntity;
import com.cybersoft.cozastore.entity.ColorEntity;
import com.cybersoft.cozastore.entity.ProductEntity;
import com.cybersoft.cozastore.entity.SizeEntity;
import com.cybersoft.cozastore.payload.request.ProductRequest;
import com.cybersoft.cozastore.payload.response.ProductResponse;
import com.cybersoft.cozastore.repository.ProductRepository;
import com.cybersoft.cozastore.service.impl.IProductService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    Gson gson = new Gson();

    @Value("${host.name}")
    private String hostName;


    @Override
//    @Cacheable(value = "getProductByCategoryId")
    public List<ProductResponse> getProductByCategoryId(int id, String hostName) {
        System.out.println("Kiem tra cache");
        List<ProductResponse> productResponsesList = new ArrayList<>();

        rabbitTemplate.convertAndSend("test exchange01","","Hello exchange01");

        // Lấy dữ liệu trên redis trước . Nếu có thì lấy dữ liệu trên redis . Nếu ko thì query data
        if (redisTemplate.hasKey("listProduct")) {
            System.out.println("Redis có dữ liệu");
            String dataProduct = (String) redisTemplate.opsForValue().get("listProduct");
            Type listType = new TypeToken<ArrayList<ProductResponse>>(){}.getType();
            productResponsesList = new Gson().fromJson(dataProduct,listType);

        } else {
            System.out.println("Redis không có dữ liệu");
            // Không tồn tại thì query database để lấy dữ liệu.
            List<ProductEntity> list = productRepository.findByCategoryId(id);
            for (ProductEntity product : list) {
                ProductResponse productResponse = new ProductResponse();
                productResponse.setId(product.getId());
                productResponse.setImage("http://" + hostName + "/product/file/" + product.getImage());
                productResponse.setName(product.getName());
                productResponse.setPrice(product.getPrice());
                productResponse.setDescription(product.getDescription());
                productResponsesList.add(productResponse);

            }
        }

        String dataProduct = gson.toJson(productResponsesList);
        // Lưu trữ dữ liệu lên redis thông qua redistemplate
        redisTemplate.opsForValue().set("listProduct", dataProduct);
        return productResponsesList;
    }

    @Override
    public ProductResponse getDetailProduct(int id) {
        Optional<ProductEntity> product = productRepository.findById(id);
        ProductResponse productResponse = new ProductResponse();
        if (product.isPresent()) {
            productResponse.setId(product.get().getId());
            productResponse.setImage(product.get().getImage());
            productResponse.setName(product.get().getName());
            productResponse.setPrice(product.get().getPrice());
            productResponse.setDescription(product.get().getDescription());
        }
        return productResponse;
    }

    @Override
    public boolean addProduct(ProductRequest productRequest) {


        try {
            ProductEntity productEntity = new ProductEntity();
            productEntity.setName(productRequest.getName());
            productEntity.setImage(productRequest.getFile().getOriginalFilename());
            productEntity.setPrice(productRequest.getPrice());
            productEntity.setQuantity(productRequest.getQuantity());
            productEntity.setDescription(productRequest.getDesc());

            ColorEntity colorEntity = new ColorEntity();
            colorEntity.setId(productRequest.getColorId());

            SizeEntity sizeEntity = new SizeEntity();
            sizeEntity.setId(productRequest.getSizeId());

            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setId(productRequest.getCategoryId());

            productEntity.setColor(colorEntity);
            productEntity.setSize(sizeEntity);
            productEntity.setCategory(categoryEntity);

            productRepository.save(productEntity);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    @CacheEvict(value = "getProductByCategoryId", allEntries = true)
    public boolean clearCache() {
        return true;
    }
}
