package com.example.entity;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Suppliers")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Supplier implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    private String address;

    @Column(name = "contact_info")
    private String contactInfo;

    @JsonIgnore
    @OneToMany(mappedBy = "supplier")
    private List<BrandSupplier> brandSuppliers;

    @JsonIgnore
    @OneToMany(mappedBy = "supplier")
    private List<SupplierProduct> supplierProducts;

    // Constructors, getters, setters, and other methods as needed.
}
