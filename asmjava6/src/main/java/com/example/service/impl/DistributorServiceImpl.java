package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Supplier;
import com.example.jparepository.SupplierRepository;
import com.example.service.DistributorService;

@Service
public class DistributorServiceImpl implements DistributorService{
    @Autowired
    private SupplierRepository supplierRepository;
    @Override
    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    @Override
    public Supplier create(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    @Override
    public Supplier findById(Integer id) {
        return supplierRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Integer id) {
        supplierRepository.deleteById(id);
    }

    @Override
    public Supplier update(Supplier supplier) {
        // Kiểm tra xem Brand có tồn tại trong cơ sở dữ liệu không
        Supplier existingBrand = supplierRepository.findById(supplier.getId()).orElse(null);
        if (existingBrand != null) {
            // Thực hiện cập nhật thông tin của Brand
            existingBrand.setName(supplier.getName());
            existingBrand.setAddress(supplier.getAddress());
            existingBrand.setContactInfo(supplier.getContactInfo());

            return supplierRepository.save(existingBrand);
        } else {
            // Nếu Brand không tồn tại, không thực hiện cập nhật và trả về null hoặc thông báo lỗi tùy vào logic ứng dụng của bạn
            return null;
        }
    }
}
