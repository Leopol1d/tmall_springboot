package com.how2java.tmall_springboot.service;

import com.how2java.tmall_springboot.dao.CategoryDAO;
import com.how2java.tmall_springboot.pojo.Category;
import com.how2java.tmall_springboot.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryDAO categoryDAO;

    public List<Category> list() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return categoryDAO.findAll(sort);
    }

    public Page4Navigator<Category> list(int start, int size, int navigatePages) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size,sort);
        Page pageFromJPA =categoryDAO.findAll(pageable);
        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }

    public void add(Category category) {
        categoryDAO.save(category);
    }
    public void delete(int id) {
        categoryDAO.delete(id);
    }
    public Category get(int id) {
        return categoryDAO.findOne(id);
    }
    public void update(Category bean) {
        categoryDAO.save(bean);
    }
}
