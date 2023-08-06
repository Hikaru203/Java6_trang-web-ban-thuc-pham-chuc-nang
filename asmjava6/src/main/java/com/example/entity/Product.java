package com.example.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(min = 1, max = 100, message = "Tên sản phẩm phải có từ 1 đến 100 ký tự")
    @JoinColumn(name = "name")
    private String name;

    @NotBlank(message = "Ảnh sản phẩm không được để trống")
    private String image;

    private String description;

    @NotNull(message = "Giá sản phẩm không được null")
    @Positive(message = "Giá sản phẩm phải là số dương")
    private BigDecimal price;

    @NotNull(message = "Danh mục sản phẩm không được null")
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "active")
    private boolean isActive;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<Cart> carts;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<Discount> discounts;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<Favorite> favorites;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<Inventory> inventories;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<SupplierProduct> supplierProducts;

    // Constructors, getters, setters, and other methods as needed.
}
