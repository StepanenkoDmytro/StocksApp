package com.stock.model.user;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "images")
@Data
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;
    @Column(name = "image_name")
    private String name;
    @Column(name = "image_origin_file_name")
    private String originFileName;
    @Column(name = "image_size")
    private Long size;
    @Column(name = "image_content_type")
    private String contentType;
    @Lob
    @Column(name = "bytes")
    private byte[] bytes;
    @OneToOne(cascade = CascadeType.PERSIST, mappedBy = "image")
    private User user;
}
