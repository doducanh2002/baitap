    package com.example.storeservice.restfullapi.service.iml.iml;

    import com.example.storeservice.restfullapi.dto.ProductDto;
    import com.example.storeservice.restfullapi.mapper.ProductMapper;
    import com.example.storeservice.restfullapi.model.Product;
    import com.example.storeservice.restfullapi.repository.StoreRepository;
    import com.example.storeservice.restfullapi.service.iml.StoreService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.cache.annotation.CacheEvict;
    import org.springframework.cache.annotation.CachePut;
    import org.springframework.cache.annotation.Cacheable;
    import org.springframework.stereotype.Service;

    import java.util.Date;
    import java.util.List;
    import java.util.stream.Collectors;

    @Service
    public class StoreServiceIml implements StoreService {

        @Autowired
        private final StoreRepository storeRepository;

        private ProductMapper productMapper;

        public StoreServiceIml(StoreRepository storeRepository, ProductMapper productMapper) {
            this.storeRepository = storeRepository;
            this.productMapper = productMapper;
        }

        @Override
        @Cacheable(value = "product")
        public ProductDto createProduct(ProductDto productDto) {
            Product product = productMapper.convertToEntity(productDto);
            return productMapper.convertToDto(storeRepository.save(product));
        }

        @Override
        @CachePut(value = "product")
        public ProductDto updateProduct(int productId, ProductDto productDto) {
            Product productReq = convertToEntity(productDto);
            Product productUpdate = storeRepository.findById(productId)
                    .map(product -> {
                        product.setName(productReq.getName());
                        product.setPrice(productReq.getPrice());
                        product.setExpired(productReq.getExpired());
                        return product;
                    })
                    .map(storeRepository::save)
                    .orElse(null);
            return convertToDto(productUpdate);
        }

        @Override
        @CacheEvict(value = "product")
        public Product deleteProduct(int productId) {
            return storeRepository.findById(productId)
                    .map(product -> {
                        storeRepository.delete(product);
                        return product;
                    })
                    .orElse(null);
        }

        @Override
        public List<Product> listProducts() {
            return storeRepository.findAll();
        }


        @Override
        public ProductDto getProductById(int productId) {
            Product product = storeRepository.findById(productId)
                    .orElse(null);
            return convertToDto(product);
        }

        @Override
        public List<ProductDto> getProductByName(String productName) {
            return storeRepository.findByName(productName)
                    .stream().map(this::convertToDto).collect(Collectors.toList());
        }

        @Override
        public List<ProductDto> getProductByPrice(int productPrice) {
            return storeRepository.findByPrice(productPrice)
                    .stream().map(this::convertToDto).collect(Collectors.toList());
        }

        @Override
        public List<ProductDto> getProductByExpired(Date productExpired) {
            return storeRepository.findByExpired(productExpired)
                    .stream().map(this::convertToDto).collect(Collectors.toList());
        }

        private Product convertToEntity(ProductDto productDto) {
            Product product = new Product();
            product.setId(productDto.getId());
            product.setName(productDto.getName());
            product.setPrice(productDto.getPrice());
            product.setExpired(productDto.getExpired());
            return product;
        }

        private ProductDto convertToDto(Product product) {
            ProductDto productDto = new ProductDto();
            product.setId(productDto.getId());
            product.setName(productDto.getName());
            product.setPrice(productDto.getPrice());
            product.setExpired(productDto.getExpired());
            return productDto;
        }
    }
