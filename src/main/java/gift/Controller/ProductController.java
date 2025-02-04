package gift.Controller;


import gift.Model.Category;
import gift.Model.Option;
import gift.Model.Product;
import gift.Service.ProductService;

import jakarta.validation.Valid;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller

public class ProductController {

    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/api/products")
    public String getProducts(Model model, Pageable pageable) {
        Page<Product> productPage = productService.findAll(pageable);
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("page", productPage);
        return "index";
    }

    @GetMapping("/api/products/add")
    public String newProductForm(Model model) {
        model.addAttribute("product", new Product(0L,"",0,"",new Category(0L,"","","","",null),null));
        model.addAttribute("categories", productService.getAllCategory());
        return "post";
    }

    @PostMapping("/api/products")
    public String createProduct(@Valid @ModelAttribute Product product) {
        productService.addProduct(product);
        return "redirect:/api/products";
    }

    @GetMapping("/api/products/update/{id}")
    public String editProductForm(@PathVariable(value = "id") Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("categories", productService.getAllCategory());
        return "update";
    }

    @PostMapping("/api/products/update/{id}")
    public String updateProduct(@PathVariable(value = "id") Long id, @Valid @ModelAttribute Product newProduct) {
        productService.updateProduct(newProduct);
        return "redirect:/api/products";
    }

    @PostMapping("/api/products/delete/{id}")
    public String deleteProduct(@PathVariable(value = "id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/api/products";
    }
}
