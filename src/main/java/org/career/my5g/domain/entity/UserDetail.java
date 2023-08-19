package org.career.my5g.domain.entity;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

// 사용자
@Entity
@Table(name = "career_user")
public class UserDetail {
    // 사용자ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "ACCOUNT")
    private String account;

    // 사용자명
    @Column(name = "NAME")
    private String name;

    // 전화번호
    @Column(name = "MOBILE")
    private String mobile;

    // 이메일주소
    @Column(name = "EMAIL")
    private String email;

    // 등록일시
    @Column(name = "CREATE_TIME")
    private ZonedDateTime createTime;

    // 등록자 ID
    @Column(name = "CREATE_BY")
    private Integer createById;

    // 레벨 ID
    @Column(name = "LEVEL_ID")
    private Integer levelId;

    // 설명
    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LEVEL_ID", insertable = false, updatable = false)
    private UserLevel level;

    @Transient
    private String remoteAddress;

    @Transient
    private ZonedDateTime loginTime;

    public UserDetail() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String loginId) {
        this.account = loginId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateById() {
        return createById;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    @JsonIgnore
    public Integer getEnterpriseBaseId() {
        return level.getEnterpriseBaseId();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public UserLevel getLevel() {
        return level;
    }

    public void setLevel(UserLevel level) {
        this.level = level;
        if (level != null) {
            this.levelId = level.getLevelId();
        }
    }

    public void setCreateById(Integer createdById) {
        this.createById = createdById;
    }

	public void withSession(OAuth2AuthenticationToken token) {
        WebAuthenticationDetails details = (WebAuthenticationDetails) token.getDetails();
        this.remoteAddress = details.getRemoteAddress();
        this.loginTime = ZonedDateTime.now(ZoneId.systemDefault());
    }

    @Override
    public String toString() {
        return "UserDetail [userId=" + userId + ", account=" + account + ", name=" + name + ", levelId=" + levelId
                + ", enterprisedId=" + level != null ? level.getEnterpriseBaseId().toString() : "[none]";
    }

}
