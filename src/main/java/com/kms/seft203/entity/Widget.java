package com.kms.seft203.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.springframework.boot.jackson.JsonComponent;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyClass;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "widget", schema = "public")
public class Widget {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "type")
    private String type;

    @Column(name = "min_width")
    private Integer minWidth;

    @Column(name = "min_height")
    private Integer minHeight;

    @ManyToOne
    @JoinColumn(name = "dashboard_id", referencedColumnName = "id")
    private Dashboard dashboard;

    @ElementCollection
    @Column(name = "configs")
   // @CollectionTable(name = "widget", joinColumns = @JoinColumn(name = "id"))
    private Map<String, String> config;
}
