package com.how2java.tmall_springboot.web;

import com.how2java.tmall_springboot.pojo.Category;
import com.how2java.tmall_springboot.service.CategoryService;
import com.how2java.tmall_springboot.util.ImageUtil;
import com.how2java.tmall_springboot.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    public Page4Navigator<Category> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        start = start<0?0:start;
        Page4Navigator<Category> page =categoryService.list(start, size, 5);  //5表示导航分页最多有5个，像 [1,2,3,4,5] 这样
        return page;
    }

    public void saveOrUpdateImageFile(Category bean, MultipartFile image, HttpServletRequest request)
            throws IOException {
        File imageFolder= new File(request.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder,bean.getId()+".jpg");
        if(!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        image.transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img, "jpg", file);
    }

    @PostMapping("/categories")
    public Category add(Category category, MultipartFile image, HttpServletRequest request) throws Exception {
        categoryService.add(category);
        saveOrUpdateImageFile(category, image, request);
        return category;
    }

    @DeleteMapping("/categories/{id}")
    public String delete(@PathVariable int id, HttpServletRequest request) {
        categoryService.delete(id);
        File imageFolder = new File(request.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, id + ".jpg");
        file.delete();
        return null;
    }

    @GetMapping("/categories/{id}")
    public Category get(@PathVariable int id) {
        return categoryService.get(id);
    }
    @PutMapping("categories/{id}")
    public Category update(Category bean, MultipartFile image, HttpServletRequest request) throws IOException {
        String name =  request.getParameter("name");
        bean.setName(name);
        categoryService.update(bean);
        if (image != null)
            saveOrUpdateImageFile(bean, image, request);
        return bean;
    }
}
