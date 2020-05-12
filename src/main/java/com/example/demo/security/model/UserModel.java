package com.example.demo.security.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

/**
 * @ClassName UserModel
 * @Description TODO
 * @Author fast
 * @Date 2020/3/3 11:43
 * @Version 1.0
 */
@Data
public class UserModel implements UserDetails {
    private Long id;
    private String name;
    private String phone;
    private String password;
    private String imageUrl;
    private Integer gender;
    private String description;
    private Integer day;
    private Integer score;
    private Integer money;
    private Collection<? extends GrantedAuthority> authorities;

    public UserModel() {

    }

    private UserModel(Builder builder) {
        setId(builder.id);
        setPhone(builder.phone);
        setName(builder.name);
        setImageUrl(builder.imageUrl);
        setPassword(builder.password);
        setDescription(builder.description);
        setGender(builder.gender);
        setDay(builder.day);
        setScore(builder.score);
        setMoney(builder.money);
        setAuthorities(builder.authorities);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(UserModel copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.phone = copy.getPhone();
        builder.name = copy.getName();
        builder.imageUrl = copy.getImageUrl();
        builder.password = copy.getPassword();
        builder.description = copy.getDescription();
        builder.gender = copy.getGender();
        builder.day = copy.getDay();
        builder.score = copy.getScore();
        builder.money = copy.getMoney();
        builder.authorities = copy.getAuthorities();
        return builder;
    }

    public static final class Builder {
        private Long id;
        private String name;
        private String phone;
        private String password;
        private String imageUrl;
        private Integer gender;
        private String description;
        private Integer day;
        private Integer score;
        private Integer money;
        private Collection<? extends GrantedAuthority> authorities;

        private Builder() {
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder phone(String val) {
            phone = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder imageUrl(String val) {
            imageUrl = val;
            return this;
        }

        public Builder gender(Integer val) {
            gender = val;
            return this;
        }

        public Builder password(String val) {
            password = val;
            return this;
        }

        public Builder day(Integer val) {
            day = val;
            return this;
        }

        public Builder score(Integer val) {
            score = val;
            return this;
        }

        public Builder money(Integer val) {
            money = val;
            return this;
        }

        public Builder authorities(Collection<? extends GrantedAuthority> val) {
            authorities = val;
            return this;
        }

        public UserModel build() {
            return new UserModel(this);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return String.valueOf(id);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
