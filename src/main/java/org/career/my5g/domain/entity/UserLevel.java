package org.career.my5g.domain.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.career.my5g.domain.converter.LevelTypeConverter;
import org.career.my5g.domain.type.LevelType;

// 사용자 레벨
@Entity
@Table(name = "x_user_level")
public class UserLevel {

    // 레벨ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LEVEL_ID")
    private Integer levelId;

    // 레벨명
    @Column(name = "LEVEL_TITLE")
    private String levelTitle;

    // 이용 기업 기본 ID
    @Column(name = "ENTERPRISE_BASE_ID")
    private Integer enterpriseBaseId;

    // 레벨 타입
    @Column(name = "LEVEL_TYPE")
    @Convert(converter = LevelTypeConverter.class)
    private LevelType levelType;

    // 설명
    @Column(name = "DESCRIPTION")
    private String description;

    public UserLevel() {
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public String getLevelTitle() {
        return levelTitle;
    }

    public void setLevelTitle(String levelTitle) {
        this.levelTitle = levelTitle;
    }

    public Integer getEnterpriseBaseId() {
        return enterpriseBaseId;
    }

    public void setEnterpriseBaseId(Integer enterpriseBaseId) {
        this.enterpriseBaseId = enterpriseBaseId;
    }

    public LevelType getLevelType() {
        return levelType;
    }

    public void setLevelType(LevelType levelType) {
        this.levelType = levelType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "UserLevel [levelId=" + levelId + ", levelTitle=" + levelTitle + ", enterpriseBaseId=" + enterpriseBaseId + "]";
    }

}
