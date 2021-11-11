package com.kms.seft203.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
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

    @Type(type = "jsonb")
    @Column(name = "configs", columnDefinition = "jsonb")
    private Map<String, Object> configs;
}
